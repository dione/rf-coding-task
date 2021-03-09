package org.acme.db

import javax.persistence.*

@Entity
@Table(name = "db_link")
class Link constructor(
        id: Long? = null,
        slug: String,
        originalUrl: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = id

    @Column
    var slug: String = slug

    @Column(name = "original_url")
    var originalUrl: String = originalUrl
}
