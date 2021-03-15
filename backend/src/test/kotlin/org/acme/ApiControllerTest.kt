package org.acme

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(PostgresResource::class)
class ApiControllerTest {
    val domain = "http://localhost:8080/"
    val API_URL = "/api/links/create"

    @Test
    fun `when no url the return 400`() {
        Given {
            header("Content-Type", "application/json")
        } When {
            post(API_URL)
        } Then {
            header("Content-Type", "application/json")
            statusCode(400)
        }
    }

    @Test
    fun `when invalid url then return 400`() {
        Given {
            header("Content-Type", "application/json")
            queryParam("originalUrl", "aaa")
        } When {
            post(API_URL)
        } Then {
            header("Content-Type", "application/json")
            statusCode(400)
        }
    }

    @Test
    fun `when valid url then return 200`() {
        val originalUrl = "https://www.google.com/search?q=url+shortener&oq=google+u&aqs=chrome.0.69i59j69i60l3j0j69i57.1069j0j7&sourceid=chrome&ie=UTF-8"
        val response: Response = Given {
            log().all()
            header("Content-Type", "application/json")
            queryParam("originalUrl", originalUrl)
        } When {
            post(API_URL)
        } Then {
            statusCode(200)

            header("Content-Type", "application/json")

            body("originalUrl", equalTo(originalUrl))
            body("slug", not(emptyOrNullString()))
            body("shortUrl", not(emptyOrNullString()))
            body("shortUrl", startsWith(domain))
        } Extract {
            response()
        }
        val shortUrl: String = response.path("shortUrl")
        val slug: String = response.path("slug")
        Assertions.assertTrue(slug.length < 10)
        Assertions.assertEquals(shortUrl, "$domain$slug")

        Given {
            log().all()
        } When {
            redirects().follow(false)
            get("$slug")
        } Then {
            statusCode(301)
            header("Location", originalUrl)
        }
    }

    @Test
    fun `when unknown slug return 404`() {
        When {
            get("123123")
        } Then {
            statusCode(404)
        }
    }
}
