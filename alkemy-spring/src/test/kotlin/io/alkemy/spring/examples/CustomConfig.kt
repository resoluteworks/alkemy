package io.alkemy.spring.examples

import io.alkemy.config.AlkemyConfig
import io.alkemy.spring.AlkemyProperties
import io.alkemy.spring.Extensions.installAlkemyExtension
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CustomConfig(alkemyProperties: AlkemyProperties, @LocalServerPort serverPort: Int) : StringSpec({
    val alkemyContext = installAlkemyExtension(
        alkemyProperties.copy(
            baseUrl = "http://localhost:${serverPort}",
        )
    )

    "run with custom config" {
        alkemyContext.config.apply {
            browser.shouldBeEqual(AlkemyConfig.DEFAULT_BROWSER)
            baseUrl.shouldBeEqual("http://localhost:${serverPort}")
            incognito.shouldBeEqual(AlkemyConfig.DEFAULT_INCOGNITO)
            maximize.shouldBeEqual(AlkemyConfig.DEFAULT_MAXIMIZE)
            headless.shouldBeEqual(true)    // set by application.yml
            windowWidth.shouldBeEqual(AlkemyConfig.DEFAULT_WINDOW_WIDTH)
            windowHeight.shouldBeEqual(AlkemyConfig.DEFAULT_WINDOW_HEIGHT)
            implicitWaitMs.shouldBeEqual(AlkemyConfig.DEFAULT_IMPLICIT_WAIT_MS)
            testSelectorAttribute.shouldBeEqual(AlkemyConfig.DEFAULT_TEST_SELECTOR_ATTRIBUTE)
        }
    }
})
