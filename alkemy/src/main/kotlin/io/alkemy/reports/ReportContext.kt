package io.alkemy.reports

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.reporter.ExtentSparkReporter
import io.alkemy.config.ReportConfig
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.BeforeProjectListener
import io.kotest.core.listeners.PrepareSpecListener
import io.kotest.core.spec.AutoScan
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

@AutoScan
object ReportContext : BeforeProjectListener, AfterProjectListener, PrepareSpecListener {

    lateinit var extent: ExtentReports
    internal val specTests = ConcurrentHashMap<String, ExtentTest>()
    internal val testNodes = ConcurrentHashMap<TestCase, ExtentTest>()

    override suspend fun beforeProject() {
        extent = ExtentReports()
        val extentSparkReporter = ExtentSparkReporter(ReportConfig.htmlReportFile)
        extent.attachReporter(extentSparkReporter)
    }

    override suspend fun afterProject() {
        extent.flush()
    }

    override suspend fun prepareSpec(kclass: KClass<out Spec>) {
        log.debug("Creating report spec test for: ${kclass.simpleName}")
        specTests[kclass.simpleName!!] = extent.createTest(kclass.simpleName)
    }

    fun createTestCaseNode(testCase: TestCase) {
        log.debug("Creating report node test for: ${testCase.spec::class.simpleName!!}.testCase.name.testName")
        val node = specTests[testCase.spec::class.simpleName!!]!!.createNode(testCase.name.testName)
        testNodes[testCase] = node
    }

    private val log = LoggerFactory.getLogger(ReportContext::class.java)
}