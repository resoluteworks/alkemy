package com.test

import io.alkemy.AlkemyContext
import io.alkemy.extensions.fillForm
import io.alkemy.extensions.submit
import io.kotest.core.spec.style.StringSpec

class ExampleTest(val context: AlkemyContext) : StringSpec({

    "login with fillForm" {
        context.apply {
            get("/login")
                .fillForm(
                    "username" to "tomsmith",
                    "password" to "SuperSecretPassword!"
                )
                .submit()

            "body" shouldHaveText "Welcome to the Secure Area"
        }
    }
})
