package io.alkemy.examples

import io.alkemy.config.AlkemyConfig
import io.alkemy.config.Browser
import io.alkemy.installAlkemyExtension
import io.kotest.core.spec.style.StringSpec

class CustomConfigPooled : StringSpec({
    val context = installAlkemyExtension(
        AlkemyConfig.fromSystemProperties().copy(
            browser = Browser.FIREFOX,
            headless = true,
            windowWidth = 800,
            windowHeight = 600,
        ),
    )

    "run with custom pooled config" {
        context.apply {
            val form = "#input-example"
            val button = "$form button"
            val input = "$form input[type='text']"
            val loading = "$form #loading"

            get("/dynamic_controls")

            button.click()
            button.shouldBeDisabled()
            loading.shouldBeVisible()
            input.shouldBeEnabled(10)
        }
    }
})