package org.acme.api

import org.acme.db.Link
import org.acme.db.LinkRepository
import org.acme.utils.SlugGenerator
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.hibernate.validator.constraints.URL
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.constraints.NotNull
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/api/links")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ApiController @Inject constructor(
        @ConfigProperty(name = "domain")
        private val domain: String,
        private val linkRepository: LinkRepository,
        private val slugGenerator: SlugGenerator,
) {
    @Path("create")
    @POST()
    @Transactional
    fun addLink(@QueryParam("originalUrl") @NotNull @URL originalUrl: String): LinkDto {
        val slug = slugGenerator.createSlug(originalUrl)

        linkRepository.persist(Link(
                slug = slug,
                originalUrl = originalUrl
        ))

        return LinkDto(
                originalUrl = originalUrl,
                shortUrl = "$domain/$slug",
                slug = slug
        )
    }

}
