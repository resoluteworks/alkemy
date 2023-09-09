plugins {
    id("alkemy.library-conventions")
    id("org.jetbrains.kotlin.plugin.spring") version ("1.8.22")
}

dependencies {
    api(project(":alkemy"))

    implementation("io.kotest:kotest-runner-junit5-jvm:${project.properties["kotestVersion"]}")
    implementation("io.kotest.extensions:kotest-extensions-spring:${project.properties["kotestSpringVersion"]}")
    implementation("org.springframework.boot:spring-boot-starter-test:${project.properties["testSpringBootVersion"]}")

    testImplementation("org.springframework.boot:spring-boot-starter-web:${project.properties["testSpringBootVersion"]}")
}
