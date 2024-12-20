package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.ExtentReportTest;
import utils.Utils;

public class SearchTest extends BaseTest {
	// success test+add logs and HTML

	@Test(description = "Search non existing text")
	public void tc01_wrongSearchTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc01_wrongSearchTest");
		headerComponent.search("aaa113");
		String toast = headerComponent.getPageToastMessage();
		try {
			Assert.assertEquals(toast, Utils.readProperty("noItemsFoundInSearch"),
					"Serach error message is not correct");
			// test.pass("Search for non-existing text shows correct error message.");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}
}
