package io.alkemy.examples

import io.alkemy.extensions.fillForm
import io.alkemy.extensions.submit
import io.alkemy.installAlkemyExtension
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class StringSelectors : StringSpec({
    val context = installAlkemyExtension()

    "string selectors and assertions" {
        context.apply {
            get("/dynamic_controls")
            "#input-example button" shouldHaveText "Enable"
            "#input-example button".click()
            "#input-example #loading".shouldBeVisible()
            "#input-example input[type='text']" shouldBeEnabled 10
            "#input-example #loading".shouldBeHidden()
        }
    }

    "string selectors and assertions - civilised version" {
        context.apply {
            val form = "#input-example"
            val button = "$form button"
            val input = "$form input[type='text']"
            val loading = "$form #loading"

            get("/dynamic_controls")

            button.shouldHaveText("Enable")
            button.shouldBeEnabled()

            button.click()
            button.shouldBeDisabled()
            loading.shouldBeVisible()
            input.shouldBeEnabled(10)
            loading.shouldBeHidden()
        }
    }

    "string selectors and assertions - chained calls" {
        context.apply {
            val form = "#input-example"
            val button = "$form button"
            val input = "$form input[type='text']"
            val loading = "$form #loading"

            get("/dynamic_controls")

            button.shouldHaveText("Enable")
                .shouldBeEnabled()
                .click()
                .shouldBeDisabled()

            loading.shouldBeVisible()
            input.shouldBeEnabled(10)
            loading.shouldBeHidden()
        }
    }

    "mix strings and WebDriver/WebElement" {
        context.apply {
            get("/login")
            webDriver.fillForm(
                "username" to "tomsmith",
                "password" to "SuperSecretPassword!"
            )
            "input[name='username']".inputValue shouldBe "tomsmith"
            webDriver.submit()
            "body" shouldHaveText "Welcome to the Secure Area"
        }
    }
})