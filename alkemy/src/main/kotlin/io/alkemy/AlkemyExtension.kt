package io.alkemy

import io.alkemy.config.AlkemyConfig
import io.kotest.core.TestConfiguration
import io.kotest.core.extensions.MountableExtension
import io.kotest.core.extensions.install
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.spec.Spec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Spec.defaultAlkemyContext() = install(AlkemyExtension(this, AlkemyConfig.fromSystemProperties()))

fun Spec.customAlkemyContext(config: AlkemyConfig) = install(AlkemyExtension(this, config))

class AlkemyExtension(
    private val testConfiguration: TestConfiguration,
    private val config: AlkemyConfig,
) : MountableExtension<AlkemyConfig, AlkemyContext>, AfterSpecListener {
    private var context: AlkemyContext? = null

    override fun mount(configure: AlkemyConfig.() -> Unit): AlkemyContext {
        config.configure()

        val context = if (AlkemyWebDriverPoolExtension.isInitialized())
            AlkemyContext.PooledDrivers(config, AlkemyWebDriverPoolExtension.webDriverPool)
        else
            AlkemyContext.NewDriver(config)

        testConfiguration.extension(context.report)

        this.context = context
        return context
    }

    override suspend fun afterSpec(spec: Spec) {
        context?.let {
            withContext(Dispatchers.IO) {
                it.close()
            }
        }
    }
}
