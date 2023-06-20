package io.alkemy.spring

import io.alkemy.AlkemyContext
import io.alkemy.config.AlkemyConfig
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration(proxyBeanMethods = false)
@Lazy   // so that this @Configuration will be applied after @Lazy @TestConfigurations
@EnableConfigurationProperties(AlkemyProperties::class)
class AlkemyConfiguration {
    @Bean
    fun defaultAlkemyConfig(alkemyProperties: AlkemyProperties) = alkemyProperties.toAlkemyConfig()

    @Bean
    fun alkemyContext(alkemyProperties: AlkemyProperties, alkemyConfig: AlkemyConfig) =
        if (alkemyProperties.pooled)
            AlkemyContext.PooledDrivers(alkemyConfig)
        else
            AlkemyContext.NewDriver(alkemyConfig)
}
