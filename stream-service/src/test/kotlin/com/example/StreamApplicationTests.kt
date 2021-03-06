package com.example

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.test.test
import java.math.BigDecimal


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StreamApplicationTests(@LocalServerPort port: Int, @Autowired val client1: WebTestClient) {

    private val client = WebClient.create("http://localhost:$port")

    @Test
    fun `Assert quotes are streamed via  sse`() {
        client.get().uri("/sse/quotes").accept(APPLICATION_JSON)
            .retrieve()
            .bodyToFlux<Quote>()
            .test()
            .consumeNextWith {
                assertEquals("GOOG", it.ticker)
                assertFalse(it.price == BigDecimal(0.0) )
            }
            .verifyComplete()
    }
}
