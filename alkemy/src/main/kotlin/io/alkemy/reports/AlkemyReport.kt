package io.alkemy.reports

import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.MediaEntityBuilder
import io.alkemy.AlkemyContext
import io.alkemy.config.ReportConfig
import io.kotest.core.listeners.AfterTestListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import java.nio.file.Paths
import kotlin.io.path.createDirectories
import kotlin.io.path.writeBytes

class AlkemyReport(
    private val context: AlkemyContext
) : BeforeTestListener, AfterTestListener {

    fun screenshot(testCase: TestCase, description: String? = null, failure: Boolean = false) {
        val node = ReportContext.testNodes[testCase]
        screenshot(node, testCase, description, failure)
    }

    private fun screenshot(node: ExtentTest?, testCase: TestCase, description: String? = null, failure: Boolean = false) {
        val clsName = testCase.spec::class.simpleName!!
        val parentDir = Paths.get(ReportConfig.screenshotDir, clsName)
        parentDir.createDirectories()
        val fileName = "${testCase.name.testName.replace(" ", "-")}-${System.currentTimeMillis()}.png"
        val pngFile = parentDir.resolve(fileName)

        val bytes = (context.webDriver as TakesScreenshot).getScreenshotAs(OutputType.BYTES)
        pngFile.writeBytes(bytes)

        if (node == null) { // if auto-scan is disabled and ReportContext is not registered
            return
        }

        // use relative paths to screenshots in report
        // we need to prepend ./ for img.src to look for the path relative to the file, not the server root
        val relativePngFilePath = "./${Paths.get(ReportConfig.htmlReportFile).parent.relativize(pngFile)}"
        val media =
            MediaEntityBuilder.createScreenCaptureFromPath(relativePngFilePath, if (failure) "Test failed" else null)
                .build()

        if (failure) {
            node.fail(description, media)
        } else {
            node.info(description, media)
        }
    }

    fun screenshotFailure(testCase: TestCase, testResult: TestResult) {
        val node = ReportContext.testNodes[testCase]
        screenshotFailure(node, testCase, testResult)
    }

    private fun screenshotFailure(node: ExtentTest?, testCase: TestCase, testResult: TestResult) {
        screenshot(node, testCase, testResult.errorOrNull?.message, true)
    }

    override suspend fun beforeAny(testCase: TestCase) {
        ReportContext.createTestCaseNode(testCase)
    }

    override suspend fun afterAny(testCase: TestCase, result: TestResult) {
        val extentNode = ReportContext.testNodes[testCase]

        if (result.isErrorOrFailure) {
            screenshotFailure(extentNode, testCase, result)
        }

        if (extentNode == null) { // if auto-scan is disabled and ReportContext is not registered
            return
        }

        if (result.isSuccess) {
            extentNode.pass("PASS")
        } else if (result.isErrorOrFailure) {
            val throwable = result.errorOrNull
            if (throwable != null) {
                extentNode.fail(throwable)
            } else {
                extentNode.fail("Unknown error")
            }
        } else if (result.isIgnored) {
            extentNode.skip(result.reasonOrNull ?: "IGNORED")
        }
    }
}

