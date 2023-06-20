package io.alkemy.examples

import io.alkemy.AlkemyContext
import io.alkemy.assertions.shouldHaveText
import io.alkemy.extensions.submit
import io.alkemy.extensions.typeInInput
import io.alkemy.pom.Page
import io.kotest.core.spec.style.StringSpec

class PomLoginTest(val context: AlkemyContext) : StringSpec() {

    init {
        "login" {
            val securePage = context
                .goTo<LoginPage>()
                .login("tomsmith", "SuperSecretPassword!")
            securePage shouldHaveText "Welcome to the Secure Area"
        }
    }
}

class LoginPage(context: AlkemyContext) : Page(context, "/login") {
    fun login(username: String, password: String): SecurePage {
        typeInInput("username", username)
        typeInInput("password", password)
        submit()
        return next<SecurePage>()
    }
}

class SecurePage(context: AlkemyContext) : Page(context, "/secure")
