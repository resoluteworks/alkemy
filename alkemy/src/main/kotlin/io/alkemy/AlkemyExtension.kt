package io.alkemy

import io.alkemy.config.AlkemyConfig
import io.kotest.core.TestConfiguration
import io.kotest.core.extensions.MountableExtension
import io.kotest.core.extensions.install
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.spec.Spec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Spec.installAlkemyExtension(
    config: AlkemyConfig = AlkemyConfig.fromSystemProperties(),
    pooled: Boolean = true,
) = install(AlkemyExtension(this, config, pooled))

class AlkemyExtension(
    private val testConfiguration: TestConfiguration,
    private val config: AlkemyConfig,
    private val pooled: Boolean,
) : MountableExtension<AlkemyConfig, AlkemyContext>, AfterSpecListener {
    private var context: AlkemyContext? = null

    override fun mount(configure: AlkemyConfig.() -> Unit): AlkemyContext {
        config.configure()

        val context = if (pooled)
            AlkemyContext.PooledDrivers(config)
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
