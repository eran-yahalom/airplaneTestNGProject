package tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.ExtentReportTest;

public class SideFilterTest extends BaseTest {
	@BeforeMethod
	public void before(Method method) {
		String testName = method.getName();
		String stars = "*".repeat(testName.length() + 20);
		System.out.println(stars);
		System.out.println("**      Running test: " + testName + "      **");
		System.out.println(stars);
	}

	@Test(description = "Test that number of items in category match number of page items")
	public void tc02_itemCategoryTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc02_itemCategoryTest");
		topMenuComponentPage.clickOnToysTab("OTHER VEHICLES");
		sideMenuComponentPage.clickOnCategoryDropDownButton();
		String count = sideMenuComponentPage.findCategoryElement("UPS");
		int numberOfHeaderItems = sideMenuComponentPage.getLinkItemCount(count);
		int pageItemsNumber = productsPage.getNumberOfItemsInPage();
		try {
			Assert.assertEquals(pageItemsNumber, numberOfHeaderItems, "Number of Items dont match");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Test if item name in list matches the searched category header")
	public void tc01_itemNameCompateTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc01_itemNameCompateTest");
		topMenuComponentPage.clickOnToysTab("OTHER VEHICLES");
		sideMenuComponentPage.clickOnCategoryDropDownButton();
		sideMenuComponentPage.findCategoryElement("UPS");
		boolean isNameFoundInProductsList = productsPage.isProductnameInProductsList("UPS");
		try {
			Assert.assertTrue(isNameFoundInProductsList, "Some items in grid do not match category search");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

}
