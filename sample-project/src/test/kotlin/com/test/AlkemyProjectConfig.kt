package com.test

import io.kotest.core.config.AbstractProjectConfig
import io.alkemy.AlkemyKotestExtension

class AlkemyProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(AlkemyKotestExtension())
}
