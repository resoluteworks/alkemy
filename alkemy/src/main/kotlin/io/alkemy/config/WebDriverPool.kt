package io.alkemy.config

import org.openqa.selenium.WebDriver
import org.slf4j.LoggerFactory
import java.util.*

typealias PooledDriver = MutableMap<AlkemyConfig, WebDriver>

class WebDriverPool : AutoCloseable {

    private val driverThreadLocal = ThreadLocal<PooledDriver>()
    private val drivers = Collections.synchronizedList(mutableListOf<WebDriver>())

    fun getDriver(config: AlkemyConfig): WebDriver {
        val driversMap = driverThreadLocal.get() ?: run {
            val map = mutableMapOf<AlkemyConfig, WebDriver>()
            driverThreadLocal.set(map)
            map
        }

        val driver = driversMap[config]
        return if (driver == null) {
            log.debug("Creating new WebDriver for this thread")
            val newDriver = config.newWebDriver()
            drivers.add(newDriver)
            driversMap[config] = newDriver
            newDriver
        } else {
            log.debug("Reusing WebDriver on this thread")
            driver
        }
    }

    override fun close() {
        log.info("Closing ${drivers.size} web drivers")
        drivers.forEach { it.close() }
    }

    companion object {
        private val log = LoggerFactory.getLogger(WebDriverPool::class.java)
    }
}