package io.alkemy.examples

import io.alkemy.AlkemyContext
import io.alkemy.config.AlkemyConfig
import io.alkemy.use
import io.alkemy.withMockRequest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe


class TestSelector(val context: AlkemyContext) : StringSpec({

    "test selector attribute" {
        val responseBody = """
            <body>
                <div data-test-selector="content-div">Secure area</div>
                <div>Footer</div>
            </body>
        """

        withMockRequest(responseBody) { baseUrl ->
            val config = AlkemyConfig(baseUrl = baseUrl)
            AlkemyContext.NewDriver(config)
                .use {
                    get("/test-selector-attribute")
                    byTestSelector("content-div").text shouldBe "Secure area"
                    "[${testSelector}=content-div]".text shouldBe "Secure area"
                }
        }
    }
})