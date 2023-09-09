package io.alkemy

import io.alkemy.config.WebDriverPool
import io.kotest.core.annotation.AutoScan
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.BeforeProjectListener

@AutoScan
internal object AlkemyWebDriverPoolExtension : BeforeProjectListener, AfterProjectListener {
    lateinit var webDriverPool: WebDriverPool

    fun isInitialized() = ::webDriverPool.isInitialized

    override suspend fun beforeProject() {
        webDriverPool = WebDriverPool()
    }

    override suspend fun afterProject() {
        webDriverPool.close()
    }
}
