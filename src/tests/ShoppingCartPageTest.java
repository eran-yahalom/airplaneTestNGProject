package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import pages.HomePage;
import pages.ProductsPage;
import utils.ExtentReportTest;
import utils.Utils;

public class ShoppingCartPageTest extends BaseTest {

	@BeforeMethod
	public void before(Method method) {
		String testName = method.getName();
		String stars = "*".repeat(testName.length() + 20);
		System.out.println(stars);
		System.out.println("**      Running test: " + testName + "      **");
		System.out.println(stars);
	}
	/* shoping cart PAGE tests, not tests on shopping cart ICON on top menu */
	/* 8 tests */

	@Test(description = "Check that number of items in cart match 'Order Summary' number")
	public void tc02_checkOrderSummeryTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc02_checkOrderSummeryTest");
		topMenuComponentPage.clickOnToysTab("Fire Vehicles");
		productsPage.addRandomProductToCart();
		headerComponent.openCart();
		headerComponent.clickOnViewAndEditCartButton();
		int numberOfItemsInCart = shoppingCartPage.getNumberOfItemsInShoppingCart();
		int numberOfItemsInOrderSummery = shoppingCartPage.getOrderSummeryItemCount();
		try {
			Assert.assertEquals(numberOfItemsInCart, numberOfItemsInOrderSummery,
					"Number of cart items does not matchorder summery");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Remove all items from Shopping Cart page")
	public void tc07_removeAllItemsFromShoppingCartPageTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc07_removeAllItemsFromShoppingCartPageTest");
		topMenuComponentPage.clickOnToysTab("Fire Vehicles");
		productsPage.addRandomProductToCart();
		headerComponent.openCart();
		headerComponent.clickOnViewAndEditCartButton();
		shoppingCartPage.removeAllItemsFromShoppingCart();
		String emptyCartText = shoppingCartPage.getEmptycartText();
		try {
			Assert.assertEquals(emptyCartText, Utils.readProperty("noItemsInShoppingCart"),
					"Cant see empty cart text/cart not empty");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Check that we can go back to home page from empty cart(by 'here' link)")
	public void tc03_goBackToHomePageTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc03_goBackToHomePageTest");
		topMenuComponentPage.clickOnToysTab("Fire Vehicles");
		productsPage.addRandomProductToCart();
		headerComponent.openCart();
		headerComponent.clickOnViewAndEditCartButton();
		shoppingCartPage.removeAllItemsFromShoppingCart();
		shoppingCartPage.clickOnHereLink();
		boolean isCartpageHeaderDisplayed = shoppingCartPage.isShoppingCartHeaderDisplayed();
		String featureProductsText = homePage.getFeaturedProductsText();
		try {
			Assert.assertFalse(isCartpageHeaderDisplayed);
			Assert.assertEquals(featureProductsText, Utils.readProperty("featureProducts"),
					"Cant see FEATURED PRODUCTS text");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Check that when inc item qty the total price will also inc")
	public void tc04_updateItemQtyTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc04_updateItemQtyTest");
		topMenuComponentPage.clickOnToysTab("Fire Vehicles");
		productsPage.addRandomProductToCart();
		headerComponent.openCart();
		headerComponent.clickOnViewAndEditCartButton();
		boolean isQtyUpdatedSuccesfully = shoppingCartPage.updateItemQtyFromShoppingCart("2");
		try {
			Assert.assertTrue(isQtyUpdatedSuccesfully, "Total price will not inc after item removal");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Enter incorrect Apply Gift Cards coupon")
	public void tc05_incorrectGiftCardCouponTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc05_incorrectGiftCardCouponTest");
		topMenuComponentPage.clickOnToysTab("Single Planes");
		productsPage.addRandomProductToCart();
		headerComponent.openCart();
		headerComponent.clickOnViewAndEditCartButton();
		shoppingCartPage.applyGiftCard("11111");
		String nessage = shoppingCartPage.getPageToastMessage();
		try {
			Assert.assertTrue(nessage.contains("is not valid."), "Message is not correct");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Enter incorrect Apply discount code")
	public void tc06_incorrectApplyDiscountCodeTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc06_incorrectApplyDiscountCodeTest");
		topMenuComponentPage.clickOnToysTab("Single Planes");
		productsPage.addRandomProductToCart();
		headerComponent.openCart();
		headerComponent.clickOnViewAndEditCartButton();
		shoppingCartPage.applyDiscointCode("11111");
		String nessage = shoppingCartPage.getPageToastMessage();
		try {
			Assert.assertTrue(nessage.contains("is not valid."), "Message is not correct");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Go to product page from cart by cliking on product name")
	public void tc01_goToProductPageByNameTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc01_goToProductPageByNameTest");
		topMenuComponentPage.clickOnToysTab("Single Planes");
		productsPage.addRandomProductToCart();
		headerComponent.openCart();
		headerComponent.clickOnViewAndEditCartButton();
		String cartProductName = shoppingCartPage.getProductName();
		shoppingCartPage.clickOnProductName();
		String prosuctPageString = productPage.getProductName();
		try {
			Assert.assertEquals(cartProductName, prosuctPageString, "Cant find product name");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Go to product page from cart by cliking on product sku")
	public void tc08_goToProductPageBySkuTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc08_goToProductPageBySkuTest");
		topMenuComponentPage.clickOnToysTab("Single Planes");
		productsPage.addRandomProductToCart();
		headerComponent.openCart();
		headerComponent.clickOnViewAndEditCartButton();
		String cartProductSku = shoppingCartPage.getProductSku();
		shoppingCartPage.clickOnProductSku();
		String prosuctPageSku = productPage.getProductSku();
		try {
			Assert.assertEquals(cartProductSku, prosuctPageSku, "Cant find product sku");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
//this fail- click on sku does not wotk
	}
}
