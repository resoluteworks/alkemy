# Alkemy Sample Project

To run this project you will need the Chrome Driver for your operating system which you can download from [here](https://chromedriver.chromium.org/downloads).

Once downloaded, please update `build.gradle` to set the path to the Chrome Driver executable:
```groovy
    systemProperty "webdriver.chrome.driver", "./chromedriver"
```

To execute all the test run the following:
```shell
./gradlew clean test
```
