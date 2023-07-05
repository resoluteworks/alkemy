package io.alkemy.examples

import io.alkemy.assertions.shouldHaveText
import io.alkemy.extensions.screenshot
import io.alkemy.extensions.submit
import io.alkemy.extensions.typeInInput
import io.alkemy.installAlkemyExtension
import io.kotest.core.spec.style.StringSpec

class Screenshots : StringSpec() {
    init {
        val context = installAlkemyExtension()

        "take screenshot" {
            val report = context.report

            val driver = context.get("/login")
            report.screenshot(testCase)

            driver.typeInInput("username", "tomsmith")
            report.screenshot(testCase, "Username entered")

            driver.typeInInput("password", "SuperSecretPassword!")
            report.screenshot(testCase, "Password entered")

            driver.submit() shouldHaveText "Welcome to the Secure Area"
        }

        "take screenshot - chained calls" {
            context.get("/login")
                .screenshot(context, testCase)
                .typeInInput("username", "tomsmith")
                .screenshot(context, testCase)
                .typeInInput("password", "SuperSecretPassword!")
                .screenshot(context, testCase)
                .submit() shouldHaveText "Welcome to the Secure Area"
        }
    }
}
