package io.alkemy.spring

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AlkemyProperties::class)
class AlkemyConfiguration
