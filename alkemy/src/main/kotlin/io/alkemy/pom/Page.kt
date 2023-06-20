package io.alkemy.pom

import io.alkemy.AlkemyContext
import org.openqa.selenium.WebDriver
import kotlin.reflect.full.primaryConstructor

abstract class Page(
    val context: AlkemyContext,
    val relativeUrl: String
) : WebDriver by context.webDriver {

    inline fun <reified P : Page> next(): P {
        return P::class.primaryConstructor!!.call(context)
    }
}
