package io.alkemy.examples

import io.alkemy.AlkemyContext
import io.alkemy.assertions.shouldBeDisabled
import io.alkemy.assertions.shouldBeEnabled
import io.alkemy.assertions.shouldBeHidden
import io.alkemy.assertions.shouldBeVisible
import io.alkemy.assertions.shouldHaveText
import io.alkemy.extensions.click
import io.alkemy.extensions.clickElement
import io.alkemy.extensions.find
import io.kotest.core.spec.style.StringSpec

class MultipleAssertions(val context: AlkemyContext) : StringSpec({

    "enabling and disabling elements" {
        val form = context.get("/dynamic_controls")
            .find("#input-example")

        val input = form.find("input[type='text']")
        val button = form.find("button")
            .shouldHaveText("Enable")
            .clickElement()

        input shouldBeEnabled 10
        button.shouldBeEnabled()
        button shouldHaveText "Disable"
        form shouldHaveText "It's enabled"

        button.clickElement()
        input shouldBeDisabled 10
        button.shouldBeEnabled()
        button shouldHaveText "Enable"
        form shouldHaveText "It's disabled!"
    }

    "enabling and disabling elements - chained element calls" {
        val driver = context.get("/dynamic_controls")

        val form = driver.find("#input-example")
        val input = form.find("input[type='text']")
        val button = form.find("button")

        button.clickElement()
            .shouldBeEnabled(10)
            .shouldHaveText("Disable")
        input.shouldBeEnabled()
        form shouldHaveText "It's enabled"

        button.clickElement()
            .shouldBeEnabled(10)
            .shouldHaveText("Enable")
        input.shouldBeDisabled()
        form shouldHaveText "It's disabled"
    }

    "enabling and disabling elements - SANE chained webdriver calls" {
        val form = "#input-example"
        val button = "$form button"
        val input = "$form input[type='text']"
        val loading = "$form #loading"
        val webDriver = context.get("/dynamic_controls")

        webDriver
            .shouldHaveText(button, "Enable")
            .shouldBeDisabled(input)

        webDriver.click(button)

        webDriver
            .shouldBeVisible(loading)
            .shouldBeDisabled(button)
            .shouldBeEnabled(input, 10)
            .shouldBeEnabled(button)
            .shouldHaveText(button, "Disable")
            .shouldHaveText(form, "It's enabled")

        webDriver.click(button)

        webDriver.shouldBeDisabled(input, 10)
            .shouldBeHidden(loading)
            .shouldHaveText(button, "Enable")
            .shouldHaveText(form, "It's disabled")
    }

    "enabling and disabling elements - MAD chained webdriver calls" {
        val form = "#input-example"
        val button = "$form button"
        val input = "$form input[type='text']"
        context.get("/dynamic_controls")
            .shouldHaveText(button, "Enable")
            .shouldBeDisabled(input)
            .click(button)
            .shouldBeDisabled(button)
            .shouldBeEnabled(input, 10)
            .shouldBeEnabled(button)
            .shouldHaveText(button, "Disable")
            .shouldHaveText(form, "It's enabled")
            .click(button)
            .shouldBeDisabled(input, 10)
            .shouldHaveText(button, "Enable")
            .shouldHaveText(form, "It's disabled")
    }
})
