package io.alkemy.extensions

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.WrapsDriver

private val REGEX_WHITESPACE = "\\s+".toRegex()

val WebElement.classes: List<String>
    get() {
        return this.getAttribute("class").split(REGEX_WHITESPACE)
    }

val WebElement.driver: WebDriver
    get() {
        return (this as WrapsDriver).wrappedDriver
    }

val WebElement.value: String
    get() {
        return getAttribute("value")
    }

fun WebElement.clearText(): WebElement {
    sendKeys(" ")
    clear()
    return this
}

fun WebElement.clickElement(): WebElement {
    this.click()
    return this
}

fun WebElement.find(cssSelector: String): WebElement {
    return findElement(By.cssSelector(cssSelector))
}
