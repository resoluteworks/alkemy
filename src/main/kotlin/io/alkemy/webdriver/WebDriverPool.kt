package io.alkemy.webdriver

import io.alkemy.config.AlkemyConfig
import io.kotest.core.annotation.AutoScan
import io.kotest.core.listeners.AfterProjectListener
import org.openqa.selenium.WebDriver
import org.slf4j.LoggerFactory
import java.util.*

internal object WebDriverPool {

    private val driverThreadLocal = ThreadLocal<WebDriver>()
    private val drivers = Collections.synchronizedList(mutableListOf<WebDriver>())

    fun getDriver(): WebDriver {
        val crtDriver = driverThreadLocal.get()
        if (crtDriver == null) {
            log.debug("Creating new WebDriver for this thread")
            val newDriver = AlkemyConfig.fromSystemProperties.newWebDriver()
            drivers.add(newDriver)
            driverThreadLocal.set(newDriver)
        } else {
            log.debug("Reusing WebDriver on this thread")
        }
        return driverThreadLocal.get()
    }

    internal fun close() {
        log.info("Closing ${drivers.size} web drivers")
        drivers.forEach { it.close() }
    }

    private val log = LoggerFactory.getLogger(WebDriverPool::class.java)
}

@AutoScan
class WebDriverPoolCleaner : AfterProjectListener {
    override suspend fun afterProject() {
        WebDriverPool.close()
    }
}
