package io.alkemy

import io.alkemy.assertions.shouldHaveText
import io.alkemy.extensions.clickElement
import io.alkemy.extensions.find
import io.alkemy.extensions.typeInInput
import io.alkemy.extensions.value
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class AlkemyContextTest : StringSpec({
    val context = defaultAlkemyContext()

    "clearText" {
        context.apply {
            get("/login")
            webDriver.typeInInput("username", "johnsmith")
                .find("input[name='username']").value shouldBe "johnsmith"
            "input[name='username']".clearText()
            "input[name='username']".inputValue shouldBe ""
        }
    }

    "find" {
        context.apply {
            get("/login")
            "form".find("button") shouldHaveText "Login"
        }
    }

    "type" {
        context.apply {
            get("/login")
            "input[name='username']".type("johnsmith").inputValue shouldBe "johnsmith"
        }
    }

    "shouldHaveClass" {
        context.apply {
            get("/login")
            "#content" shouldHaveClass "large-12"
        }
    }

    "shouldBeEnabled" {
        context.apply {
            val form = "#input-example"
            get("/dynamic_controls")
            "$form button" shouldHaveText "Enable"
        }
    }

    "shouldBeEnabled with timeout" {
        context.apply {
            get("/dynamic_controls")
            "#input-example button".click()
            "#input-example input[type='text']" shouldBeEnabled 10
        }
    }

    "shouldBeDisabled" {
        context.apply {
            val form = "#input-example"
            val button = "$form button"
            get("/dynamic_controls")
            button.click()
            button.shouldBeDisabled()
        }
    }

    "shouldBeDisabled with timeout" {
        context.apply {
            val form = "#input-example"
            val button = "$form button"
            val input = "$form input[type='text']"
            get("/dynamic_controls")
            button.click()
            input shouldBeDisabled 10
        }
    }

    "shouldBeVisible" {
        context.apply {
            get("/dynamic_controls")
            "#input-example button".click()
            "#input-example #loading".shouldBeVisible()
        }
    }

    "shouldBeHidden" {
        context.apply {
            get("/dynamic_controls")
            "#input-example button".click()
            "#input-example input[type='text']" shouldBeEnabled 10
            "#input-example #loading".shouldBeHidden()
        }
    }

    "get absolute" {
        context.getAbsolute("https://the-internet.herokuapp.com") shouldHaveText "Welcome to the-internet"
    }

    "wait" {
        val driver = context.get("/dynamic_controls")

        val form = driver.find("#input-example")
        val button = form.find("button")

        button.clickElement()
        context.wait(seconds = 10) {
            button.isEnabled
        }
    }
})
