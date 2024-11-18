plugins {
    id("jacoco-report-aggregation")
    id("com.glovoapp.semantic-versioning") version "1.1.10"
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.16"
    id("com.gradleup.nmcp")
}

repositories {
    mavenCentral()
}

dependencies {
    subprojects.forEach { subproject ->
        jacocoAggregation(project(":${subproject.name}"))
    }
}

reporting {
    reports {
        val testCodeCoverageReport by creating(JacocoCoverageReport::class) {
            testType.set(TestSuiteType.UNIT_TEST)
        }
    }
}

coverallsJacoco {
    reportPath = "build/reports/jacoco/testCodeCoverageReport/testCodeCoverageReport.xml"
}

tasks.coverallsJacoco {
    mustRunAfter("testCodeCoverageReport")
}

tasks.register("codeCoverage") {
    dependsOn("testCodeCoverageReport")
    dependsOn("coverallsJacoco")
}
