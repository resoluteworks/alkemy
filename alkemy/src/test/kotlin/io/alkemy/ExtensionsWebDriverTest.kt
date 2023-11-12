package io.alkemy

import io.alkemy.extensions.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ExtensionsWebDriverTest : StringSpec({
    val context = defaultAlkemyContext()

    "clearInput" {
        context.apply {
            get("/login")
            webDriver.typeInInput("username", "johnsmith")
                .find("input[name='username']").value shouldBe "johnsmith"
            webDriver.clearInput("username")
            "input[name='username']".inputValue shouldBe ""
        }
    }

    "wait" {
        val driver = context.get("/dynamic_controls")

        val form = driver.find("#input-example")
        val button = form.find("button")

        button.clickElement()
        driver.wait(10) {
            button.isEnabled
        }
    }

    "clearText" {
        context.apply {
            get("/login")
            webDriver.typeInInput("username", "johnsmith")
                .find("input[name='username']").value shouldBe "johnsmith"
            webDriver.clearText("input[name='username']")
            "input[name='username']".inputValue shouldBe ""
        }
    }
})
