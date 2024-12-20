package tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.*;

public class ShoppingCartDialogTest extends BaseTest {

	@BeforeMethod
	public void before(Method method) {
		String testName = method.getName();
		String stars = "*".repeat(testName.length() + 20);
		System.out.println(stars);
		System.out.println("     Running test: " + testName + "       ");
		System.out.println(stars);
	}

	/* 4 tests */

	@Test(description = "Check that cart badge is 0 when cart is empty")
	public void tc01_checkEmptycartTes() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc01_checkEmptycartTes");
		int cartBadgeNumber = headerComponent.getCartBadgeCount();
		headerComponent.openCart();
		boolean isCartEmpty = headerComponent.getEmptyCartText();
		headerComponent.closeCart();
		try {
			Assert.assertTrue(isCartEmpty);
			Assert.assertEquals(cartBadgeNumber, 0, "Card badge is not 0 when cart is empty");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Check that after selecting a dropdown element, the element will show in the bredcrumbs")
	public void tc02_breadcrumbsTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc02_breadcrumbsTest");
		topMenuComponentPage.clickOnToysTab("Construction Toys");
		String bredCrumbNameString = breadcrumbs.getBreadCrumbName();
		try {
			Assert.assertEquals(bredCrumbNameString, Utils.readProperty("constructionToys"),
					"Cant see element in brascrumbs");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Add item to cart")
	public void tc04_checksssEmptycart() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc04_checksssEmptycart");
		topMenuComponentPage.clickOnToysTab("Construction Toys");
		String itemNumber = productsPage.addRandomProductToCart();
		headerComponent.openCart();
		boolean isItemIncart = headerComponent.isItemInCart(itemNumber);
		headerComponent.closeCart();
		try {
			Assert.assertTrue(isItemIncart, "Item was not added to cart");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Check filter by number")
	public void tc03_checksssEmptycart() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc03_checksssEmptycart");
		topMenuComponentPage.clickOnToysTab("Construction Toys");
		sortFilterComponentPage.filterByNumberOfItems("15");
		int numberOfItems = productsPage.getNumberOfItemsInPage();
		String elements = Integer.toString(numberOfItems);
		try {
			Assert.assertEquals(elements, "15", "Filter results are not correct");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	// click on cart icon from list

}
