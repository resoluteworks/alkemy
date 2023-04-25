<img width="606" alt="2023-04-24_18-26-03" src="https://user-images.githubusercontent.com/2995576/234071539-5eec3c7e-0b66-49cf-aa55-58a664002817.png">

# Alkemy

![GitHub release (latest by date)](https://img.shields.io/github/v/release/cosmin-marginean/alkemy)
![Coveralls](https://img.shields.io/coverallsCoverage/github/cosmin-marginean/alkemy)

Alkemy is a browser automation framework written in [Kotlin](https://kotlinlang.org/) and based on [Selenium](https://www.selenium.dev/) and [Kotest](https://kotest.io/).
The objective is to provide more fluent definitions for Selenium tests, using a functional style and Kotlin extension functions. Alkemy currently supports Chrome and Firefox browsers. 

Some of the features includ [extension functions and assertions](https://github.com/cosmin-marginean/alkemy#writing-tests), [automatic report generation](https://github.com/cosmin-marginean/alkemy/wiki/Reports-and-screenshots), [custom test selector](https://github.com/cosmin-marginean/alkemy/wiki/Test-selector-attribute) and [running multiple browsers in parallel](https://github.com/cosmin-marginean/alkemy/wiki/Running-tests-in-parallel).

* See [Documentation](https://github.com/cosmin-marginean/alkemy/wiki) for further information.
* Various examples tests can be found [here](https://github.com/cosmin-marginean/alkemy/tree/main/src/test/kotlin/io/alkemy/examples).
* For a full working project setup please check the [sample-project](https://github.com/cosmin-marginean/alkemy/tree/main/sample-project).

## Dependency
```groovy
testImplementation "io.resoluteworks:alkemy:${alkemyVersion}"
```

## Writing tests
Alkemy enables a variety of approaches for writing Selenium tests in Kotlin, depending on your design preference.

### String selectors
A set of extensions functions can be used against `String` to perform lookups and assertions.
```kotlin
class MyTest(val context: AlkemyContext) : StringSpec({

    "string selectors and assertions" {
        // To use String extensions the context.apply{} construct is required
        context.apply {
            get("/dynamic_controls")
            
            val form = "#input-example"
            "$form button".shouldHaveText("Enable")
                .shouldBeEnabled()
                .click()
                .shouldBeDisabled()

            "$form #loading".shouldBeVisible()
            "$form input[type='text']".shouldBeEnabled(maxWaitSeconds = 10)
        }
    }
```

### WebDriver & WebElement extensions
Similar extensions are available for `WebDriver` and `WebElement`, including
helper methods like `fillForm`, `typeIn` and assertions like `shouldBeVisible`, `shoulHaveClass`, etc.

```kotlin
    "login with fillForm" {
        context.get("/login")
            .fillForm(
                "username" to "tomsmith",
                "password" to "SuperSecretPassword!"
            )
            .submit() shouldHaveText "Welcome to the Secure Area"
    }
```

### Custom test selector attribute
Alkemy can use a globally defined `data-*` HTML attribute to lookup elements.
Read more about this [here](https://github.com/cosmin-marginean/alkemy/wiki/Test-selector-attribute).
```html
<body>
    <div data-test-selector="content-div">Secure area</div>
    <div>Footer</div>
</body>
```
```kotlin
byTestSelector("content-div").text shouldBe "Secure area"
```

### Page Object Model
Alkemy also provides a very basic framework for Page Object Model approaches. This includes all the extensions and
assertions available for `WebDriver`.

```kotlin
class LoginPage(context: AlkemyContext) : Page(context, "/login") {
    fun login(username: String, password: String): SecurePage {
        fillForm("username" to username, "password" to password)
            .submit()
        return next<SecurePage>()
    }
}

class SecurePage(context: AlkemyContext) : Page(context, "/secure")


"login with page object model" {
    val securePage = context
        .goTo<LoginPage>()
        .login("tomsmith", "SuperSecretPassword!")
    securePage shouldHaveText "Welcome to the Secure Area"
}
    
```

### Using native Kotest assertions
Any Kotest assertions can be used natively in combination with the Alkemy or Selenium objects.

```kotlin
    "using native Kotest assertions" {
        val driver = context.get("/login")
        driver.findElements("input") shouldHaveSize 2
        driver.find("h2").text shouldContain "Login Page"
    }
```


## Documentation
See [Documentation](https://github.com/cosmin-marginean/alkemy/wiki) for further information.
