package io.alkemy.browsers

import io.alkemy.config.AlkemyConfig
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.slf4j.LoggerFactory
import java.time.Duration


private val log = LoggerFactory.getLogger("io.alkemy.chrome")

fun firefoxDriver(config: AlkemyConfig): WebDriver {
    log.info("Creating Firefox WebDriver with config $config")

    val options = FirefoxOptions()

    if (config.incognito) {
        options.addArguments("--private")
    }

    if (config.headless) {
        options.addArguments("-headless")
    }

    val driver = FirefoxDriver(options)
    val manager = driver.manage()
    if (config.maximize && !config.headless) {
        manager.window().maximize()
    } else {
        manager.window().size = Dimension(config.windowWidth, config.windowHeight)
    }

    manager.timeouts().implicitlyWait(Duration.ofMillis(config.implicitWaitMs))
    return driver
}

