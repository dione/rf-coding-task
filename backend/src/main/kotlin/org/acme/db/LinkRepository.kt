package org.acme.db

import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class LinkRepository : PanacheRepository<Link> {
    fun findBySlug(slug: String): Link? = find("slug = ?1", slug).firstResult()
}
