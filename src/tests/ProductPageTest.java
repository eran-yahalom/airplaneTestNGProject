package tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.ExtentReportTest;
import utils.Utils;

public class ProductPageTest extends BaseTest {
	// 4 tests done

	@BeforeMethod
	public void before(Method method) {
		String testName = method.getName();
		String stars = "*".repeat(testName.length() + 20);
		System.out.println(stars);
		System.out.println("     Running test: " + testName + "       ");
		System.out.println(stars);
	}

	@Test(description = "Go to product page from search and validate its the correct item")
	public void tc01_goToProductPageBySkuTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc01_goToProductPageBySkuTest");
		headerComponent.search("CORGI");
		String productsPageName = productsPage.clickOnSelectedProductByName("CORGI");
		String productPageName = productPage.getProductName();
		String breadCrumbName = breadcrumbs.getBreadCrumbName();
		try {
			Assert.assertEquals(productsPageName, productPageName, "Products name do not match(1)");
			Assert.assertEquals(productsPageName, breadCrumbName, "Products name do not match(2)");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(dataProvider = "getSearchString", description = "Go to products page from search and see the search string is all product names")
	public void tc02_searchWoordInProductsnamesTest(String searchString) {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc02_searchWoordInProductsnamesTest");
		headerComponent.search(searchString);
		boolean isNameFoundInProductsList = productsPage.isProductnameInProductsList(searchString);
		try {
			Assert.assertTrue(isNameFoundInProductsList, "One product in list does not match the search string");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());// The assertion failure message
			throw e;

		}

// BUS is not found in all products
	}

	@Test(description = "Get success toast after updating item qty")
	public void tc03_updateProductSuccessToastTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc03_updateProductSuccessToastTest");
		headerComponent.search("MTA");
		productsPage.clickOnSelectedProductByName("MTA");
		productPage.setProductQuantity("2");
		productPage.clickOnAddItemTocartButton();
		boolean isToastDisplayed = productPage.isSuccessToastDisplayed();
		try {
			Assert.assertTrue(isToastDisplayed, "Toast was not displayed");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Update product number")
	public void tc04_searchWoordInProductsnamesTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc04_searchWoordInProductsnamesTest");
		topMenuComponentPage.clickOnToysTab("Jet Airplanes");
		productsPage.addRandomProductToCart();
		headerComponent.openCart();
		String cartProductQtyBefore = headerComponent.getProductQuantity();
		int cartItemBefore = Utils.convertStringToInt(cartProductQtyBefore);
		headerComponent.clickOnProductIcon();
		String beforeQty = productPage.getProductQuantity();
		int prductPageItemBefore = Utils.convertStringToInt(beforeQty);
		productPage.setProductQuantity("2");
		productPage.clickOnAddItemTocartButton();
		headerComponent.openCart();
		String productQtyString = headerComponent.getProductQuantity();
		int updatedItemsInCart = Utils.convertStringToInt(productQtyString);
		try {
			Assert.assertEquals(cartItemBefore + 2, updatedItemsInCart, "Product number was not updated succesfully");
			// ifcartProductQtyString=1 , if we set 2 then ifcartProductQtyString=1+2=3
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@DataProvider
	public Object[][] getSearchString() {
		Object[][] myData = { { "DOLL" }, { "BUSS" }, };
		return myData;
	}
}
