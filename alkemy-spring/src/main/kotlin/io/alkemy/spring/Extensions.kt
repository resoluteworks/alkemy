package io.alkemy.spring

import io.alkemy.AlkemyExtension
import io.kotest.core.extensions.install
import io.kotest.core.spec.Spec

object Extensions {
    fun Spec.installAlkemyExtension(
        properties: AlkemyProperties,
    ) = install(AlkemyExtension(this, properties.toAlkemyConfig(), properties.pooled))
}
