package io.alkemy.config

object ReportConfig {
    val enabled: Boolean = System.getProperty("alkemy.report.enabled", "true").toBoolean()
    val screenshotDir: String = System.getProperty("alkemy.report.screenshotDir", "build/reports/screenshots")
    val htmlReportFile: String = System.getProperty("alkemy.report.htmlReport", "build/reports/extent-report.html")
}