package io.alkemy.spring.examples

import io.alkemy.AlkemyContext
import io.alkemy.config.AlkemyConfig
import io.alkemy.spring.AlkemyProperties
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Primary

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CustomConfig(alkemyContext: AlkemyContext) : StringSpec() {
    @TestConfiguration
    @Lazy   // required to use @LocalServerPort in @TestConfiguration
    class Configuration {
        @LocalServerPort
        private lateinit var serverPort: Number

        @Bean
        @Primary
        fun customAlkemyConfig(alkemyProperties: AlkemyProperties) = alkemyProperties.copy(
            baseUrl = "http://localhost:${serverPort}",
        ).toAlkemyConfig()
    }

    @LocalServerPort
    private lateinit var serverPort: Number

    init {
        "run with custom config" {
            with(alkemyContext.config) {
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
    }
}
