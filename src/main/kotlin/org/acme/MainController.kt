package org.acme

import org.acme.db.LinkRepository
import java.net.URI
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

@Path("/")
class MainController @Inject constructor(
        private val linkRepository: LinkRepository,
) {
    @Path("{slug}")
    @GET()
    fun redirect(@PathParam("slug") slug: String) =
            linkRepository.findBySlug(slug)
                    ?.let { it.originalUrl }
                    ?.let { Response.status(Response.Status.MOVED_PERMANENTLY).location(URI(it)).build() }
                    ?: Response.status(Response.Status.NOT_FOUND).entity("Not Found").build()
}
