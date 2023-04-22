package io.alkemy.browsers

import io.alkemy.config.AlkemyConfig
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.LoggerFactory
import java.time.Duration

private val log = LoggerFactory.getLogger("io.alkemy.chrome")

fun chromeDriver(config: AlkemyConfig): WebDriver {
    log.info("Creating Chrome WebDriver with config $config")

    val options = ChromeOptions()

    if (config.incognito) {
        options.addArguments("--incognito")
    }

    if (config.headless) {
        options.addArguments("--headless")
        options.addArguments("window-size=${config.windowWidth},${config.windowHeight}");
    }

    val driver = ChromeDriver(options)
    val manager = driver.manage()
    if (config.maximize && !config.headless) {
        manager.window().maximize()
    }

    manager.timeouts().implicitlyWait(Duration.ofMillis(config.implicitWaitMs))
    return driver
}

