package data.source.remote

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.Charsets
import io.ktor.client.plugins.CurlUserAgent
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

object ApiClient {
    val client: HttpClient by lazy {
        HttpClient(CIO) {
            CurlUserAgent()

            Charsets {
                register(Charsets.UTF_8)
            }

            install(Logging) {
                this.logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v("Ktor HTTP Client", null, message)
                    }

                }
                this.level = LogLevel.ALL
            }.also { Napier.base(DebugAntilog())}

            install(HttpTimeout) {
                this.requestTimeoutMillis = requestTimeoutMillis
                this.connectTimeoutMillis = connectTimeoutMillis
                this.socketTimeoutMillis = socketTimeoutMillis
            }
        }
    }
}