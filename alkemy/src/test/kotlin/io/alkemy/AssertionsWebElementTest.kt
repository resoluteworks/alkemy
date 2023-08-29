package io.alkemy

import io.alkemy.assertions.*
import io.alkemy.extensions.click
import io.alkemy.extensions.find
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import org.openqa.selenium.TimeoutException

class AssertionsWebElementTest : StringSpec({
    val context = defaultAlkemyContext()

    "shouldHaveText" {
        context.get("/hovers").find("body") shouldHaveText "Hover over the image for additional information"
    }

    "shouldHaveClass" {
        context.get("/hovers").find("#content div") shouldHaveClass "example"
    }

    "shouldHaveClasses" {
        context.get("/hovers").find("#content").shouldHaveClasses("large-12", "columns")
    }

    "shouldBeEnabled" {
        context.get("/dynamic_controls").find("#input-example button").shouldBeEnabled()
    }

    "shouldBeEnabled with timeout" {
        val form = "#input-example"
        val button = "$form button"
        val input = "$form input[type='text']"
        context.get("/dynamic_controls")
            .shouldHaveText(button, "Enable")
            .shouldBeDisabled(input)
            .click(button)
            .shouldBeDisabled(button)
            .find(input).shouldBeEnabled(10)
    }

    "shouldBeEnabled with timeout - breaking" {
        shouldThrow<TimeoutException> {
            val form = "#input-example"
            val button = "$form button"
            val input = "$form input[type='text']"
            context.get("/dynamic_controls")
                .shouldHaveText(button, "Enable")
                .shouldBeDisabled(input)
                .click(button)
                .shouldBeDisabled(button)
                .find(input).shouldBeEnabled(1)
        }
    }

    "shouldBeDisabled" {
        context.get("/dynamic_controls").find("#input-example input").shouldBeDisabled()
    }

    "shouldBeDisabled with timeout" {
        val form = "#input-example"
        val button = "$form button"
        val input = "$form input[type='text']"
        val webDriver = context.get("/dynamic_controls")

        webDriver.click(button)
            .shouldBeEnabled(input, 10)
            .click(button)
            .find(input).shouldBeDisabled(10)
    }

    "shouldBeDisabled with timeout - breaking" {
        shouldThrow<TimeoutException> {
            val form = "#input-example"
            val button = "$form button"
            val input = "$form input[type='text']"
            val webDriver = context.get("/dynamic_controls")

            webDriver.click(button)
                .shouldBeEnabled(input, 10)
                .click(button)
                .find(input)
                .shouldBeDisabled(1)
        }
    }

    "shouldBeVisible" {
        val form = "#input-example"
        val button = "$form button"
        val loading = "$form #loading"
        context.get("/dynamic_controls")
            .click(button)
            .find(loading).shouldBeVisible()
    }

    "shouldBeHidden" {
        val form = "#input-example"
        val button = "$form button"
        val input = "$form input[type='text']"
        val loading = "$form #loading"
        val webDriver = context.get("/dynamic_controls")

        webDriver.click(button)
            .shouldBeEnabled(input, 10)
            .click(button)
            .shouldBeDisabled(input, 10)
            .find(loading).shouldBeHidden()
    }
})
