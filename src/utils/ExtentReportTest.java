package utils;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportTest {
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>(); // For thread-safe tests

	// Static block for initializing ExtentReports
	static {
		createExtentReports(); // Initialize ExtentReports
	}

	// Initialize ExtentReports and attach the reporter
	private static void createExtentReports() {
		if (extent == null) {
			ExtentSparkReporter spark = new ExtentSparkReporter("reports/Spark.html");
			extent = new ExtentReports();
			extent.attachReporter(spark);
			extent.setSystemInfo("Environment", "QA-Test");
			extent.setSystemInfo("Tester", "Eran Y");
		}
	}

	// Method to create a test instance
	public static ExtentTest createTest(String testName) {
		ExtentTest test = extent.createTest(testName);
		extentTest.set(test); // Assign test to the thread
		return test;
	}

	// Get the current test instance
	public static ExtentTest getTest() {
		return extentTest.get();
	}

	public static String getCurrentClass() {
		// Get the stack trace of the current thread
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

		// Get the class name of the method that called this method (index 2, since
		// index 0 and 1 are related to Thread and getStackTrace method)
		String className = stackTraceElements[2].getClassName();

		// Return the simple class name (without the package)
		return className.substring(className.lastIndexOf('.') + 1);
	}

	public static String getCurrentClassWithLineNumber() {
		// Get the stack trace of the current thread
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

		// Get the class name of the method that called this method (index 2, since
		// index 0 and 1 are related to Thread and getStackTrace method)
		String className = stackTraceElements[2].getClassName();

		// Get the line number where the exception occurred
		int lineNumber = stackTraceElements[2].getLineNumber();

		// Return the simple class name and the line number
		return className.substring(className.lastIndexOf('.') + 1) + ":" + lineNumber;
	}

	public static void printExceptionToHTML(Exception e, String errorInClass, String text) {
		String stackTrace = Utils.getStackTrace(e);
		ExtentReportTest.getTest().fail(errorInClass + " - " + text).fail("<pre>" + e.getMessage() + "</pre>");//
		Assert.fail("Test failed due to an exception: " + e.getMessage());
	}

	// Method to flush the report
	public static void flush() {
		if (extent != null) {
			extent.flush();
		}
	}
}
