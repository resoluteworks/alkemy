plugins {
    id("java-library")
    kotlin("jvm")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
    id("jacoco")
}

group = "io.resoluteworks"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.dokkaHtml {
    outputDirectory = File(rootDir, "docs/dokka/${project.name}")
}

kotlin {
    jvmToolchain(8)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
    }
    systemProperty("kotest.framework.parallelism", "4")
}

tasks.jacocoTestReport {
    reports {
        html.required = true
        xml.required = true
    }
}

tasks.withType<PublishToMavenRepository> {
    dependsOn("test")
}

publishing {
    val publishGit = "cosmin-marginean/alkemy"

    repositories {
        mavenLocal()
        maven {
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("NEXUS_USERNAME")
                password = System.getenv("NEXUS_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name = project.name
                description = "${project.properties["publishDescription"]}"
                url = "https://github.com/${publishGit}"
                licenses {
                    license {
                        name = "Apache License 2.0"
                        url = "https://github.com/${publishGit}/blob/main/LICENSE"
                        distribution = "repo"
                    }
                }
                scm {
                    url = "https://github.com/${publishGit}"
                    connection = "scm:git:git://github.com/${publishGit}.git"
                    developerConnection = "scm:git:ssh://git@github.com:${publishGit}.git"
                }
                developers {
                    developer {
                        name = "${project.properties["developer"]}"
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
