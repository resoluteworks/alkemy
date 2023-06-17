package io.alkemy.examples

import io.alkemy.AlkemyContext
import io.alkemy.config.AlkemyConfig
import io.alkemy.config.Browser
import io.alkemy.use
import io.kotest.core.spec.style.StringSpec

class CustomConfigPooled : StringSpec({

    "run with custom pooled config" {
        val config = AlkemyConfig.fromSystemProperties()
            .copy(
                browser = Browser.FIREFOX,
                headless = false,
                windowWidth = 800,
                windowHeight = 600
            )
        val context = AlkemyContext.PooledDrivers(config)
        context.use {
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