package io.alkemy

import io.alkemy.extensions.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ExtensionsWebDriverTest : StringSpec({
    val context = installAlkemyExtension()

    "clearInput" {
        context.apply {
            get("/login")
            webDriver.typeInInput("username", "johnsmith")
                .find("input[name='username']").value shouldBe "johnsmith"
            webDriver.clearInput("username")
            "input[name='username']".inputValue shouldBe ""
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
