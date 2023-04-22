package io.alkemy.extensions

import io.alkemy.AlkemyContext
import io.kotest.core.test.TestCase
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

fun WebDriver.typeIn(inputName: String, text: String): WebDriver {
    findElement(By.cssSelector("input[name='${inputName}']"))
        .sendKeys(text)
    return this
}

fun WebDriver.submit(): WebDriver {
    findElement(By.cssSelector("button[type='submit']")).click()
    return this
}

fun WebDriver.find(cssSelector: String): WebElement {
    return findElement(By.cssSelector(cssSelector))
}

fun WebDriver.click(cssSelector: String): WebDriver {
    find(cssSelector).click()
    return this
}

fun WebDriver.clearText(cssSelector: String): WebDriver {
    find(cssSelector).clearText()
    return this
}

val WebDriver.text: String
    get() {
        return findElement(By.tagName("body")).text
    }

fun WebDriver.fillForm(values: Map<String, String>): WebDriver {
    values.forEach { (field, fieldValue) ->
        typeIn(field, fieldValue)
    }
    return this
}

fun WebDriver.fillForm(vararg values: Pair<String, String>): WebDriver {
    return fillForm(values.toMap())
}

fun WebDriver.clearInput(inputName: String): WebDriver {
    findElement(By.cssSelector("input[name='${inputName}']")).clearText()
    return this
}

fun WebDriver.screenshot(context: AlkemyContext, testCase: TestCase): WebDriver {
    context.report.screenshot(testCase)
    return this
}

