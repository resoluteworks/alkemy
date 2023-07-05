package io.alkemy.examples

import io.alkemy.assertions.shouldHaveText
import io.alkemy.extensions.fillForm
import io.alkemy.extensions.find
import io.alkemy.extensions.submit
import io.alkemy.installAlkemyExtension
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.StringSpec

// Comment @Ignored to run this failing test
@Ignored
class FailingTest : StringSpec({
    val context = installAlkemyExtension()

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
