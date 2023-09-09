package io.alkemy

import io.alkemy.assertions.*
import io.alkemy.extensions.click
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException

class AssertionsWebdriverTest : StringSpec({
    val context = defaultAlkemyContext()

    "shouldHaveText" {
        context.get("/hovers") shouldHaveText "Hover over the image for additional information"
    }

    "shouldHaveText with CSS selector" {
        context.get("/hovers").shouldHaveText(".example p", "Hover over the image for additional information")
    }

    "elementShouldExist" {
        context.get("/hovers") elementShouldExist By.cssSelector("div.example")
    }

    "tagShouldExist" {
        context.get("/hovers") tagShouldExist "h3"
    }

    "shouldBeEnabled" {
        context.get("/dynamic_controls").shouldBeEnabled("#input-example button")
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
            .shouldBeEnabled(input, 10)
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
                .shouldBeEnabled(input, 1)
        }
    }

    "shouldBeDisabled" {
        context.get("/dynamic_controls").shouldBeDisabled("#input-example input")
    }

    "shouldBeDisabled with timeout" {
        val form = "#input-example"
        val button = "$form button"
        val input = "$form input[type='text']"
        val webDriver = context.get("/dynamic_controls")

        webDriver.click(button)
            .shouldBeEnabled(input, 10)
            .click(button)
            .shouldBeDisabled(input, 10)
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
                .shouldBeDisabled(input, 1)
        }
    }

    "shouldBeVisible" {
        val form = "#input-example"
        val button = "$form button"
        val loading = "$form #loading"
        val webDriver = context.get("/dynamic_controls")
        webDriver.click(button)
            .shouldBeVisible(loading)
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
        webDriver
            .shouldBeDisabled(input, 10)
            .shouldBeHidden(loading)
    }
})
