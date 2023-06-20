package io.alkemy

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

fun withMockRequest(body: String, block: (String) -> Unit) {
    val server = MockWebServer()
    server.enqueue(MockResponse().setBody(body))
    val baseUrl = "http://${server.hostName}:${server.port}"
    block(baseUrl)
}