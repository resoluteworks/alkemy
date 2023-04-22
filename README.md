# Alkemy

Alkemy is a browser automation framework written in [Kotlin](https://kotlinlang.org/) and based on [Selenium](https://www.selenium.dev/) and [Kotest](https://kotest.io/).
The objective is to provide more fluent definitions for Selenium tests, using a functional style and Kotlin extension functions. Alkemy currently supports Chrome and Firefox browsers. 

Please see Documentation section below for complete details.

For a full working example please check the [sample-project](https://github.com/cosmin-marginean/alkemy/tree/main/sample-project) in this repository.

## Dependency
```groovy
testImplementation "io.resoluteworks:alkemy:${alkemyVersion}"
```

## Writing tests
Alkemy enables a variety of approaches for writing Selenium tests in Kotlin. A set of extensions functions can be
used against `String` to perform lookups and assertions.

Alternatively, similar extensions are available for `WebDriver` and `WebElement`, including
helper methods like `fillForm`, `typeIn` and assertions like `shouldBeVisible`, `shoulHaveClass`, etc.

Lastly, Alkemy provides a basic framework for Page Object Model approaches. This includes all the extensions and
assertions available for `WebDriver`.

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

    "login with fillForm" {
        context.get("/login")
            .fillForm(
                "username" to "tomsmith",
                "password" to "SuperSecretPassword!"
            )
            .submit() shouldHaveText "Welcome to the Secure Area"
    }

    "login with page object model" {
        val securePage = context
            .goTo<LoginPage>()
            .login("tomsmith", "SuperSecretPassword!")
        securePage shouldHaveText "Welcome to the Secure Area"
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
* [Configuration reference](https://github.com/cosmin-marginean/alkemy/wiki/Alkemy-Configuration-Reference)
