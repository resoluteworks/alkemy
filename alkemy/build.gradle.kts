plugins {
    id("alkemy.library-conventions")
}

tasks.test {
    systemProperty("alkemy.browser", "chrome")
    systemProperty("alkemy.baseUrl", "https://the-internet.herokuapp.com")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.kotest:kotest-assertions-core:${project.properties["kotestVersion"]}")
    implementation("io.kotest:kotest-property:${project.properties["kotestVersion"]}")
    implementation("io.kotest:kotest-runner-junit5-jvm:${project.properties["kotestVersion"]}")
    implementation("org.seleniumhq.selenium:selenium-api:${project.properties["seleniumVersion"]}")
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:${project.properties["seleniumVersion"]}")
    implementation("org.seleniumhq.selenium:selenium-firefox-driver:${project.properties["seleniumVersion"]}")
    implementation("org.seleniumhq.selenium:selenium-support:${project.properties["seleniumVersion"]}")
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("com.aventstack:extentreports:5.0.9")

    testImplementation("ch.qos.logback:logback-classic:1.3.7")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
}
