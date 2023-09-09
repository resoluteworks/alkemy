# Alkemy Spring Module

Module to configure Alkemy using Spring configuration properties

## Usage

Gradle:

```groovy
// build.gradle
testImplementation "io.resoluteworks:alkemy-spring:${alkemyVersion}"
```

Autowire `AlkemyProperties` in the constructor together with other Spring beans, then use it to register the
`AlkemyExtension`, e.g.

```kotlin
class MySpec(alkemyProperties: AlkemyProperties, service: UserService) : WordSpec({
    val alkemyContext = alkemyContext(alkemyProperties)
```

Configure Alkemy using Spring configuration properties, e.g.

```yaml
# src/test/resources/application.yml
alkemy:
  base-url: https://the-internet.herokuapp.com
  headless: true
```

## Dynamic Configuration

To configure Alkemy dynamically, use `AlkemyProperties`' copy constructor to overwrite any properties.

For example, to configure `alkemy.base-url` with the webserver port that was randomly assigned by Spring Boot Test:

```kotlin
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DynamicConfigurationTest(alkemyProperties: AlkemyProperties, @LocalServerPort serverPort: Int) : WordSpec() {
    init {
        val alkemyContext = alkemyContext(
            alkemyProperties.copy(
                baseUrl = "http://localhost:${serverPort}",
            )
        )
```

## Disabling Kotest Auto Scan

If Kotest [auto scan](https://kotest.io/docs/framework/project-config.html#runtime-detection) is disabled, you will need
to manually load the `AlkemyWebDriverPoolExtension` and `SpringAutowireConstructorExtension` extension in your Project
config, e.g.

```kotlin
class ProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(AlkemyWebDriverPoolExtension, SpringAutowireConstructorExtension)
}
```
