plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.0")
    implementation("com.gradleup.nmcp:com.gradleup.nmcp.gradle.plugin:0.0.9")
}
