package io.alkemy.spring

import io.alkemy.config.AlkemyConfig
import io.alkemy.config.Browser
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(AlkemyProperties.CONFIGURATION_PROPERTIES_PREFIX)
@ConstructorBinding
data class AlkemyProperties(
    val baseUrl: String?,
    val browser: Browser = AlkemyConfig.DEFAULT_BROWSER,
    val incognito: Boolean = AlkemyConfig.DEFAULT_INCOGNITO,
    val maximize: Boolean = AlkemyConfig.DEFAULT_MAXIMIZE,
    val headless: Boolean = AlkemyConfig.DEFAULT_HEADLESS,
    val windowWidth: Int = AlkemyConfig.DEFAULT_WINDOW_WIDTH,
    val windowHeight: Int = AlkemyConfig.DEFAULT_WINDOW_HEIGHT,
    val implicitWaitMs: Long = AlkemyConfig.DEFAULT_IMPLICIT_WAIT_MS,
    val testSelectorAttribute: String = AlkemyConfig.DEFAULT_TEST_SELECTOR_ATTRIBUTE,
) {
    companion object {
        const val CONFIGURATION_PROPERTIES_PREFIX = "alkemy"
    }

    fun toAlkemyConfig() = AlkemyConfig(
        baseUrl
            ?: throw IllegalStateException("${AlkemyProperties::class.simpleName}.${::baseUrl.name} must not be null"),
        browser,
        incognito,
        maximize,
        headless,
        windowWidth,
        windowHeight,
        implicitWaitMs,
        testSelectorAttribute,
    )
}
