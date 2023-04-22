package io.alkemy

import io.alkemy.assertions.shouldHaveText
import io.alkemy.extensions.fillForm
import io.alkemy.extensions.submit
import io.alkemy.pom.Page
import io.kotest.core.spec.style.StringSpec

class MyTest(val context: AlkemyContext) : StringSpec({

    "string selectors and assertions" {
        // To use String extensions the context.apply{} construct is required
        context.apply {
            get("/dynamic_controls")

            val form = "#input-example"
            "$form button".shouldHaveText("Enable")
                .shouldBeEnabled()
                .click()
                .shouldBeDisabled()

            "$form #loading".shouldBeVisible()
            "$form input[type='text']".shouldBeEnabled(maxWaitSeconds = 10)
        }
    }

    "login with fillForm" {
        context.get("/login")
            .fillForm(
                "username" to "tomsmith",
                "password" to "SuperSecretPassword!"
            )
            .submit() shouldHaveText "Welcome to the Secure Area"
    }

    "login with page object model" {
        val securePage = context
            .goTo<LoginPage>()
            .login("tomsmith", "SuperSecretPassword!")
        securePage shouldHaveText "Welcome to the Secure Area"
    }
})

class LoginPage(context: AlkemyContext) : Page(context, "/login") {
    fun login(username: String, password: String): SecurePage {
        fillForm("username" to username, "password" to password)
            .submit()
        return next<SecurePage>()
    }
}

class SecurePage(context: AlkemyContext) : Page(context, "/secure")
