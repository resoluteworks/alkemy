package com.test

import io.alkemy.AlkemyContext
import io.alkemy.assertions.shouldHaveClass
import io.alkemy.assertions.shouldHaveClasses
import io.alkemy.assertions.shouldHaveText
import io.alkemy.extensions.clearInput
import io.alkemy.extensions.fillForm
import io.alkemy.extensions.find
import io.alkemy.extensions.submit
import io.alkemy.extensions.typeIn
import io.kotest.core.spec.style.StringSpec

class Login(val context: AlkemyContext) : StringSpec({

    "login with fillForm" {
        context
            .get("/login")
            .fillForm(
                "username" to "tomsmith",
                "password" to "SuperSecretPassword!"
            )
            .submit() shouldHaveText "Welcome to the Secure Area"
    }

    "login by typing in input" {
        context
            .get("/login")
            .typeIn("username", "tomsmith")
            .typeIn("password", "SuperSecretPassword!")
            .submit() shouldHaveText "Welcome to the Secure Area"
    }

    "login by typing in input - multiple assertions" {
        val webDriver = context
            .get("/login")
            .typeIn("username", "tomsmith")
            .typeIn("password", "SuperSecretPassword!")
            .submit()
            .shouldHaveText("Welcome to the Secure Area")

        val button = webDriver.find(".button")
        button shouldHaveText "Logout"
        button shouldHaveClass "secondary"
        button.shouldHaveClasses("secondary", "radius")

        val h2 = webDriver.find("h2")
        h2 shouldHaveText "Secure Area"
    }

    "login by typing in input - enter something, then clear input, then enter again" {
        context
            .get("/login")
            .typeIn("username", "tomsmith")
            .typeIn("password", "wrong password")
            .clearInput("password")
            .typeIn("password", "SuperSecretPassword!")
            .submit() shouldHaveText "Welcome to the Secure Area"
    }
})
