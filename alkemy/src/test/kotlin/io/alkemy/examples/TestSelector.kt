package io.alkemy.examples

import io.alkemy.config.AlkemyConfig
import io.alkemy.installAlkemyExtension
import io.alkemy.withMockRequest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe


class TestSelector : StringSpec({
    val responseBody = """
            <body>
                <div data-test-selector="content-div">Secure area</div>
                <div>Footer</div>
            </body>
        """

    withMockRequest(responseBody) { baseUrl ->
        val context = installAlkemyExtension(
            AlkemyConfig(baseUrl = baseUrl),
            false,
        )

        "test selector attribute" {
            context.apply {
                get("/test-selector-attribute")
                byTestSelector("content-div").text shouldBe "Secure area"
                "[${testSelector}=content-div]".text shouldBe "Secure area"
            }
        }
    }
})