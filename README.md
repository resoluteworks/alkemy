<img width="606" alt="2023-04-24_18-26-03" src="https://user-images.githubusercontent.com/2995576/234071539-5eec3c7e-0b66-49cf-aa55-58a664002817.png">

# Alkemy

![GitHub release (latest by date)](https://img.shields.io/github/v/release/cosmin-marginean/alkemy)
![Coveralls](https://img.shields.io/coverallsCoverage/github/cosmin-marginean/alkemy)

Alkemy is a browser automation framework written in [Kotlin](https://kotlinlang.org/) and based on [Selenium](https://www.selenium.dev/) and [Kotest](https://kotest.io/).
The objective is to provide more fluent definitions for Selenium tests, using a functional style and Kotlin extension functions. Alkemy currently supports Chrome and Firefox browsers. 

Some of the feature highlights are [extension functions and assertions](https://github.com/cosmin-marginean/alkemy#writing-tests) for Strings, WebDriver and WebElement, [automatic report generation](https://github.com/cosmin-marginean/alkemy/wiki/Reports-and-screenshots), and [running multiple browsers in parallel](https://github.com/cosmin-marginean/alkemy/wiki/Running-tests-in-parallel).

* See [Documentation](https://github.com/cosmin-marginean/alkemy/wiki/Alkemy-Documentation) for further information.
* For various example tests please check the tests [here](https://github.com/cosmin-marginean/alkemy/tree/main/src/test/kotlin/io/alkemy/examples).
* For a full working project setup please check the [sample-project](https://github.com/cosmin-marginean/alkemy/tree/main/sample-project).

## Dependency
```groovy
testImplementation "io.resoluteworks:alkemy:${alkemyVersion}"
```

## Writing tests
Alkemy enables a variety of approaches for writing Selenium tests in Kotlin. A set of extensions functions can be
used against `String` to perform lookups and assertions.


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


Alternatively, similar extensions are available for `WebDriver` and `WebElement`, including
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


Alkemy also provides a very basic framework for Page Object Model approaches. This includes all the extensions and
assertions available for `WebDriver`.

```kotlin
    "login with page object model" {
        val securePage = context
            .goTo<LoginPage>()
            .login("tomsmith", "SuperSecretPassword!")
        securePage shouldHaveText "Welcome to the Secure Area"
    }
    
    ...
    
class LoginPage(context: AlkemyContext) : Page(context, "/login") {
    fun login(username: String, password: String): SecurePage {
        fillForm("username" to username, "password" to password)
            .submit()
        return next<SecurePage>()
    }
}

class SecurePage(context: AlkemyContext) : Page(context, "/secure")
```


Lastly, any Kotest assertions can be used natively in combination with the Alkemy or Selenium objects.

```kotlin
    "using native Kotest assertions" {
        val driver = context.get("/login")
        driver.findElements("input") shouldHaveSize 2
        driver.find("h2").text shouldContain "Login Page"
    }
})

class LoginPage(context: AlkemyContext) : Page(context, "/login") {
    fun login(username: String, password: String): SecurePage {
        fillForm("username" to username, "password" to password)
            .submit()
        return next<SecurePage>()
    }
}

class SecurePage(context: AlkemyContext) : Page(context, "/secure")
```


## Documentation
See [Documentation](https://github.com/cosmin-marginean/alkemy/wiki/Alkemy-Documentation) for further information.
