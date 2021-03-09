package org.acme

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.PostgreSQLContainer

class PostgresResource : QuarkusTestResourceLifecycleManager {
    companion object {
        val db = PostgreSQLContainer<Nothing>("postgres:13").apply {
            withDatabaseName("urlshort")
            withUsername("u_urlshort")
            withPassword("p_urlshort")
        }
    }

    override fun start(): Map<String, String> {
        db.start()
        return mapOf(
                "quarkus.datasource.jdbc.url" to db.jdbcUrl
        )
    }

    override fun stop() {
        db.stop()
    }
}
