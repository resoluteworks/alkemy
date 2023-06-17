package io.alkemy

import io.kotest.core.config.AbstractProjectConfig

class AlkemyProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(AlkemyKotestExtension())
}