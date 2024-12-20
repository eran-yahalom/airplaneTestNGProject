package tests;

import java.lang.reflect.Method;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.ExtentReportTest;
import utils.Utils;

public class FilterAndSortTest extends BaseTest {

	@BeforeMethod
	public void before(Method method) {
		String testName = method.getName();
		String stars = "*".repeat(testName.length() + 20);
		System.out.println(stars);
		System.out.println("     Running test: " + testName + "       ");
		System.out.println(stars);
	}

	/* 3 pass 1 fail (bug in page-test3) */
	@Test(description = "See items in List mode")
	public void tc02_seeItemsInListViewTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc02_seeItemsInListViewTest");
		topMenuComponentPage.clickOnToysTab("HELICOPTERS");
		int numOfGridElements = productsPage.getNumberOfItemsInPage();
		sortFilterComponentPage.clickOnListButton();
		int numberOfListElements = sortFilterComponentPage.countItemsInList();
		try {
			Assert.assertEquals(numOfGridElements, numberOfListElements, "Number of items does not match");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Order grid items by SKU")
	public void tc03_orderItemsBySkuTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc03_orderItemsBySkuTest");
		topMenuComponentPage.clickOnToysTab("HELICOPTERS");
		sortFilterComponentPage.sortBy("sku");
		boolean isSorted = productsPage.checkItemsAreSortedBySku("SKU");
		try {
			Assert.assertTrue(isSorted, "Items are not ordered by SKU");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
		// fail because in page sku is not ordered
	}

	@Test(description = "Order grid items by Price")
	public void tc01_orderItemsByPriceTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc01_orderItemsByPriceTest");
		topMenuComponentPage.clickOnToysTab("FIRE VEHICLES");
		sortFilterComponentPage.sortBy("price");
		boolean isSorted = productsPage.checkItemsAreSortedByPrice("Price");
		try {
			Assert.assertTrue(isSorted, "Items are not ordered by Price");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}

	@Test(description = "Order list items by Price")
	public void tc04_orderListItemsByPriceTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc04_orderListItemsByPriceTest");
		topMenuComponentPage.clickOnToysTab("FIRE VEHICLES");
		sortFilterComponentPage.sortBy("price");
		sortFilterComponentPage.clickOnListButton();
		boolean isSorted = productsPage.checkListItemsAreSortedByPrice("Price");
		try {
			Assert.assertTrue(isSorted, "Items are not ordered by Price");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

}