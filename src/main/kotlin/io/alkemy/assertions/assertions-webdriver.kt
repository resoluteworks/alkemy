package io.alkemy.assertions

import io.alkemy.extensions.find
import io.alkemy.extensions.text
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

infix fun WebDriver.shouldHaveText(text: String): WebDriver {
    this.text shouldContain text
    return this
}

fun WebDriver.shouldHaveText(cssSelector: String, text: String): WebDriver {
    find(cssSelector) shouldHaveText text
    return this
}

infix fun WebDriver.elementShouldExist(by: By): WebDriver {
    findElement(by) shouldNotBe null
    return this
}

infix fun WebDriver.tagShouldExist(tagName: String): WebDriver {
    return elementShouldExist(By.tagName(tagName))
}

fun WebDriver.shouldBeEnabled(cssSelector: String): WebDriver {
    find(cssSelector).shouldBeEnabled()
    return this
}

fun WebDriver.shouldBeEnabled(cssSelector: String, maxWaitSeconds: Int): WebDriver {
    find(cssSelector).shouldBeEnabled(maxWaitSeconds)
    return this
}

fun WebDriver.shouldBeDisabled(cssSelector: String): WebDriver {
    find(cssSelector).shouldBeDisabled()
    return this
}

fun WebDriver.shouldBeDisabled(cssSelector: String, maxWaitSeconds: Int): WebDriver {
    find(cssSelector).shouldBeDisabled(maxWaitSeconds)
    return this
}

fun WebDriver.shouldBeVisible(cssSelector: String): WebDriver {
    find(cssSelector).shouldBeVisible()
    return this
}

fun WebDriver.shouldBeHidden(cssSelector: String): WebDriver {
    find(cssSelector).shouldBeHidden()
    return this
}

