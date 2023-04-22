package io.alkemy.config

import io.alkemy.browsers.chromeDriver
import io.alkemy.browsers.firefoxDriver
import org.openqa.selenium.WebDriver

data class AlkemyConfig(
    val browser: Browser = Browser.valueOf(System.getProperty("alkemy.browser", "chrome").trim().uppercase()),
    val baseUrl: String = System.getProperty("alkemy.baseUrl"),
    val incognito: Boolean = System.getProperty("alkemy.incognito", "true").toBoolean(),
    val maximize: Boolean = System.getProperty("alkemy.maximize", "true").toBoolean(),
    val headless: Boolean = System.getProperty("alkemy.headless", "false").toBoolean(),
    val windowWidth: Int = System.getProperty("alkemy.windowWidth", "0").toInt(),
    val windowHeight: Int = System.getProperty("alkemy.windowHeight", "0").toInt(),
    val implicitWaitMs: Long = System.getProperty("alkemy.implicitWaitMs", "5000").toLong(),
    val testSelectorAttribute: String = System.getProperty("alkemy.testSelectorAttribute", "data-test-selector"),
    val reportConfig: ReportConfig = ReportConfig(),
) {

    fun newWebDriver(): WebDriver {
        return when (browser) {
            Browser.CHROME -> chromeDriver(this)
            Browser.FIREFOX -> firefoxDriver(this)
        }
    }

    companion object {
        val fromSystemProperties = AlkemyConfig()
    }
}

enum class Browser {
    CHROME,
    FIREFOX
}