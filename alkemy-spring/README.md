# Alkemy Spring Module

Alkemy module to integrate with Spring Boot.

## Usage

Gradle:

```groovy
// build.gradle
testImplementation "io.resoluteworks:alkemy-spring:${alkemyVersion}"
```

:warning: **Do NOT load [AlkemyKotestExtension](../README.md#kotest-extension).**

Autowire `AlkemyContext` in the constructor together with other Spring beans, e.g.

```kotlin
class MySpec(alkemyContext: AlkemyContext, service: UserService) : WordSpec() {
```

Configure Alkemy using Spring configuration properties, e.g.

```yaml
# src/test/resources/application.yml
alkemy:
  base-url: https://the-internet.herokuapp.com
  headless: true
```

## Dynamic Configuration

To configure Alkemy dynamically, overwrite the `AlkemyConfig` bean in the test configuration.

For example, to configure `alkemy.base-url` with the webserver port that was randomly assigned
by Spring Boot Test:

```kotlin
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DynamicConfigurationTest(alkemyContext: AlkemyContext) : WordSpec() {
    @TestConfiguration
    @Lazy   // required to use @LocalServerPort in @TestConfiguration
    class Configuration {
        @LocalServerPort
        private lateinit var serverPort: Number

        @Bean
        @Primary
        fun customAlkemyConfig(alkemyProperties: AlkemyProperties) = alkemyProperties.copy(
            baseUrl = "http://localhost:${serverPort}",
        ).toAlkemyConfig()
    }
```

:warning: Make sure to call `AlkemyProperties#copy()` before calling `AlkemyProperties#toAlkemyConfig()`. Calling
`AlkemyConfig#copy()` after calling `AlkemyProperties#toAlkemyConfig()` will throw an exception if `baseUrl` is not yet
specified.

## Excluding Alkemy from a test

To exclude Alkemy from a test, exclude `AlkemyConfiguration` using `@EnableAutoConfiguration#exclude`, e.g.

```kotlin
@SpringBootTest
@EnableAutoConfiguration(exclude = [AlkemyConfiguration::class])
class MySpec(service: UserService) : WordSpec() {
```

## Disabling Kotest Auto Scan

If Kotest [auto scan](https://kotest.io/docs/framework/project-config.html#runtime-detection) is disabled, you will need
to manually load the `SpringAutowireConstructorExtension` and `AlkemySpringKotestExtension` extensions in your Project
config, e.g.

```kotlin
class ProjectConfig : AbstractProjectConfig() {
   override fun extensions() = listOf(SpringAutowireConstructorExtension, AlkemySpringKotestExtension)
}
```
