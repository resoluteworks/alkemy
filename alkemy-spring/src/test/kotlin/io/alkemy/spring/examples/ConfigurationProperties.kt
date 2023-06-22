package io.alkemy.spring.examples

import io.alkemy.AlkemyContext
import io.alkemy.config.AlkemyConfig
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ConfigurationProperties(alkemyContext: AlkemyContext) : StringSpec() {
    init {
        "configure Alkemy using Spring Configuration Properties" {
            with(alkemyContext.config) {
                browser.shouldBeEqual(AlkemyConfig.DEFAULT_BROWSER)
                baseUrl.shouldBeEqual("https://the-internet.herokuapp.com")
                incognito.shouldBeEqual(AlkemyConfig.DEFAULT_INCOGNITO)
                maximize.shouldBeEqual(AlkemyConfig.DEFAULT_MAXIMIZE)
                headless.shouldBeEqual(true)
                windowWidth.shouldBeEqual(AlkemyConfig.DEFAULT_WINDOW_WIDTH)
                windowHeight.shouldBeEqual(AlkemyConfig.DEFAULT_WINDOW_HEIGHT)
                implicitWaitMs.shouldBeEqual(AlkemyConfig.DEFAULT_IMPLICIT_WAIT_MS)
                testSelectorAttribute.shouldBeEqual(AlkemyConfig.DEFAULT_TEST_SELECTOR_ATTRIBUTE)
            }
        }
    }
}
