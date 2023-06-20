package io.alkemy.assertions

import io.alkemy.extensions.classes
import io.alkemy.extensions.driver
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


infix fun WebElement.shouldHaveText(text: String): WebElement {
    this.text shouldContain text
    return this
}

infix fun WebElement.shouldHaveClass(text: String): WebElement {
    this.classes shouldContain text
    return this
}

fun WebElement.shouldHaveClasses(vararg classes: String): WebElement {
    classes.forEach {
        this.classes shouldContain it
    }
    return this
}

fun WebElement.shouldBeEnabled(): WebElement {
    this.isEnabled shouldBe true
    return this
}

infix fun WebElement.shouldBeEnabled(maxWaitSeconds: Int): WebElement {
    WebDriverWait(driver, Duration.ofSeconds(maxWaitSeconds.toLong())).until {
        this.isEnabled
    }
    return this
}

infix fun WebElement.shouldBeDisabled(maxWaitSeconds: Int): WebElement {
    WebDriverWait(driver, Duration.ofSeconds(maxWaitSeconds.toLong())).until {
        !this.isEnabled
    }
    return this
}

fun WebElement.shouldBeDisabled(): WebElement {
    this.isEnabled shouldBe false
    return this
}

fun WebElement.shouldBeVisible(): WebElement {
    this.isDisplayed shouldBe true
    return this
}

fun WebElement.shouldBeHidden(): WebElement {
    this.isDisplayed shouldBe false
    return this
}
