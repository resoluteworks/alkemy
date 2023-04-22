package io.alkemy

import io.alkemy.config.AlkemyConfig
import io.alkemy.webdriver.WebDriverPool
import io.kotest.core.extensions.ConstructorExtension
import io.kotest.core.spec.AutoScan
import io.kotest.core.spec.Spec
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.typeOf

@AutoScan
class AlkemySpecConstructor : ConstructorExtension {

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        val constructor = clazz.primaryConstructor!!
        val contextType = typeOf<AlkemyContext>()

        val context = AlkemyContext(AlkemyConfig.fromSystemProperties) { WebDriverPool.getDriver() }

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
}