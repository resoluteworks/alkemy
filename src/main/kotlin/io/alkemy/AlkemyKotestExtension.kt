package io.alkemy

import io.alkemy.config.AlkemyConfig
import io.kotest.core.extensions.ConstructorExtension
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.BeforeProjectListener
import io.kotest.core.spec.Spec
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.typeOf

class AlkemyKotestExtension : BeforeProjectListener, ConstructorExtension, AfterProjectListener {

    lateinit var context: AlkemyContext

    override suspend fun beforeProject() {
        val config = AlkemyConfig.fromSystemProperties()
        context = AlkemyContext.PooledDrivers(config)
    }

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        val constructor = clazz.primaryConstructor!!
        val contextType = typeOf<AlkemyContext>()

        // Constructor injection
        if (constructor.parameters.size == 1 && constructor.parameters.first().type == contextType) {
            val spec = constructor.call(context)
            spec.extensions(context.report)
            return spec
        }

        // Injection via lateinit var
        val contextProp = clazz.declaredMemberProperties.find { it.returnType == contextType }
        if (contextProp != null) {
            val spec = constructor.call()
            (contextProp as KMutableProperty<*>).setter.call(spec, context)
            spec.extensions(context.report)
            return spec
        }

        // Leave it to the framework otherwise
        return null
    }

    override suspend fun afterProject() {
        context.close()
    }
}