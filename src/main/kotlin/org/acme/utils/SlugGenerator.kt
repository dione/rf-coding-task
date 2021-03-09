package org.acme.utils

import org.hibernate.Session
import org.hibernate.type.LongType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SlugGenerator @Inject constructor(
        private val session: Session
) {
    fun createSlug(originalUrl: String): String = Base62.encode(incrementAndGet())

    private fun incrementAndGet() = session.createSQLQuery("select nextval('slug_seq') as next")
            .addScalar("next", LongType())
            .uniqueResult() as Long
}
