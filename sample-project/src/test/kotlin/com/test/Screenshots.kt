package com.test

import io.alkemy.AlkemyContext
import io.alkemy.assertions.shouldHaveText
import io.alkemy.extensions.screenshot
import io.alkemy.extensions.submit
import io.alkemy.extensions.typeIn
import io.kotest.core.spec.style.StringSpec

class Screenshots(val context: AlkemyContext) : StringSpec() {

    init {
        "take screenshot" {
            val report = context.report

            val driver = context.get("/login")
            report.screenshot(testCase)

            driver.typeIn("username", "tomsmith")
            report.screenshot(testCase, "Username entered")

            driver.typeIn("password", "SuperSecretPassword!")
            report.screenshot(testCase, "Password entered")

            driver.submit() shouldHaveText "Welcome to the Secure Area"
        }

        "take screenshot - chained calls" {
            context.get("/login")
                .screenshot(context, testCase)
                .typeIn("username", "tomsmith")
                .screenshot(context, testCase)
                .typeIn("password", "SuperSecretPassword!")
                .screenshot(context, testCase)
                .submit() shouldHaveText "Welcome to the Secure Area"
        }
    }
}
