package io.alkemy.config

import io.alkemy.browsers.chromeDriver
import io.alkemy.browsers.firefoxDriver
import io.alkemy.internals.enumSysProp
import io.alkemy.internals.sysProp
import org.openqa.selenium.WebDriver

data class AlkemyConfig(
    val baseUrl: String,
    val browser: Browser = DEFAULT_BROWSER,
    val incognito: Boolean = DEFAULT_INCOGNITO,
    val maximize: Boolean = DEFAULT_MAXIMIZE,
    val headless: Boolean = DEFAULT_HEADLESS,
    val windowWidth: Int = DEFAULT_WINDOW_WIDTH,
    val windowHeight: Int = DEFAULT_WINDOW_HEIGHT,
    val implicitWaitMs: Long = DEFAULT_IMPLICIT_WAIT_MS,
    val testSelectorAttribute: String = DEFAULT_TEST_SELECTOR_ATTRIBUTE
) {

    fun newWebDriver(): WebDriver {
        return when (browser) {
            Browser.CHROME -> chromeDriver(this)
            Browser.FIREFOX -> firefoxDriver(this)
        }
    }

    companion object {
        val DEFAULT_BROWSER = Browser.CHROME
        const val DEFAULT_INCOGNITO = true
        const val DEFAULT_MAXIMIZE = true
        const val DEFAULT_HEADLESS = false
        const val DEFAULT_WINDOW_WIDTH = 0
        const val DEFAULT_WINDOW_HEIGHT = 0
        const val DEFAULT_IMPLICIT_WAIT_MS = 5000L
        const val DEFAULT_TEST_SELECTOR_ATTRIBUTE = "data-test-selector"

        fun fromSystemProperties(): AlkemyConfig {
            return AlkemyConfig(
                baseUrl = System.getProperty("alkemy.baseUrl"),
                browser = enumSysProp("alkemy.browser", DEFAULT_BROWSER),
                incognito = sysProp("alkemy.incognito", DEFAULT_INCOGNITO),
                maximize = sysProp("alkemy.maximize", DEFAULT_MAXIMIZE),
                headless = sysProp("alkemy.headless", DEFAULT_HEADLESS),
                windowWidth = sysProp("alkemy.windowWidth", DEFAULT_WINDOW_WIDTH),
                windowHeight = sysProp("alkemy.windowHeight", DEFAULT_WINDOW_HEIGHT),
                implicitWaitMs = sysProp("alkemy.implicitWaitMs", DEFAULT_IMPLICIT_WAIT_MS),
                testSelectorAttribute = sysProp("alkemy.testSelectorAttribute", DEFAULT_TEST_SELECTOR_ATTRIBUTE)
            )
        }
    }
}

enum class Browser {
    CHROME,
    FIREFOX
}