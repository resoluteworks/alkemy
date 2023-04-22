package com.test

import io.alkemy.AlkemyContext
import io.alkemy.assertions.shouldHaveText
import io.alkemy.extensions.fillForm
import io.alkemy.extensions.find
import io.alkemy.extensions.submit
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.StringSpec

@Ignored
class FailingTest(val context: AlkemyContext) : StringSpec({

    "failing test to showcase screenshot on error" {
        context
            .get("/login")
            .fillForm(
                "username" to "tomsmith",
                "password" to "SuperSecretPassword!"
            )
            .submit()
            .find("h2") shouldHaveText "Spice world!"
    }
})
