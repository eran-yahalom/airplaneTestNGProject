package tests;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import components.HeaderComponentPage;
import components.TopMenuComponentPage;
import pages.CustomerLoginPage;
import pages.MyAccountPage;
import pages.MyWishListPage;
import pages.PaymentMethodPage;
import pages.ProductPage;
import pages.ProductsPage;
import pages.ShippingAddressPage;
import utils.ExtentReportTest;
import utils.Utils;

public class MyWishListTest extends BaseTest {
	private ExtentTest test;
	private WebDriver incognitoDriver;

	@BeforeClass
	@Override
	public void setUp() {
		System.out.println("Skipping BaseTest browser setup for incognito browser.");
		ExtentReportTest.createTest(this.getClass().getSimpleName() + " Setup");
	}

	@BeforeMethod
	public void setUpIncognitoBrowser() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");

		incognitoDriver = new ChromeDriver(options);
		incognitoDriver.manage().window().maximize();
		incognitoDriver.get("https://www.airplaneshop.com/");
		loginPage = new CustomerLoginPage(incognitoDriver);
		headerComponent = new HeaderComponentPage(incognitoDriver);
		productsPage = new ProductsPage(incognitoDriver);
		productPage = new ProductPage(incognitoDriver);
		topMenuComponentPage = new TopMenuComponentPage(incognitoDriver);
		myWishListPage = new MyWishListPage(incognitoDriver);
		myAccountPage = new MyAccountPage(incognitoDriver);
		
		loginPage.login(Utils.readProperty("user"), Utils.readProperty("password"));
		headerComponent.openCart();
		headerComponent.removeAllItemFromCart();
	}

	@BeforeMethod
	public void before(Method method) {
		String testName = method.getName();
		String stars = "*".repeat(testName.length() + 20);
		System.out.println(stars);
		System.out.println("     Running test: " + testName + "       ");
		System.out.println(stars);
	}
	/* 8 tests */
	// all tests fail

	@Test(description = "Add item to whishList as anonymous user")
	public void tc08_anonymousAddItemToWishListTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc08_anonymousAddItemToWishListTest");
		headerComponent.logOut();
		headerComponent.search("MTA");
		productsPage.clickOnSelectedProductByName("MTA");
		productPage.clickOnAddToWishListLink();
		String pageHeader = headerComponent.getPageHaeder();
		String errorMessaheString = loginPage.getPageToastMessage();
		try {
			Assert.assertEquals(pageHeader, "CUSTOMER LOGIN");
			Assert.assertEquals(errorMessaheString, "You must login or register to add items to your wishlist.");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Add item to whishList as logged in user")
	public void tc06_loggedInUserAddItemToWishListTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc06_loggedInUserAddItemToWishListTest");
		topMenuComponentPage.clickOnToysTab("Single Planes");
		productsPage.clickOnSelectedProductByName("AIRBUS");
		String productPageName = productPage.getProductName();
		productPage.clickOnAddToWishListLink();
		String toastString = myWishListPage.getPageToast();
		try {
			Assert.assertEquals(toastString, productPageName + " " + Utils.readProperty("myWishListSussessToast"));
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Check that wish list is empty")
	public void tc01_wishListIsEmptyTexst() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc01_wishListIsEmptyTexst");
		headerComponent.clickOnMyAccountButton();
		myAccountPage.clickOnMyaccountTab("My Wish List");
		myWishListPage.removeAllItemFromWishList();
		boolean isButtonDisplayed = myWishListPage.isbackButtonDisplayed();
		String getToast = myWishListPage.getEmptyPageToast();
		try {
			Assert.assertTrue(isButtonDisplayed, "Back button is not displayed");
			Assert.assertEquals(getToast, Utils.readProperty("emptyWishListText"),
					"Empty wish list toast is not displayed");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Check number of items in list after add")
	public void tc02_wishListItemsCount() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc02_wishListItemsCount");
		headerComponent.clickOnMyAccountButton();
		myAccountPage.clickOnMyaccountTab("My Wish List");
		int itemsBefore = myWishListPage.getWishListItemsCount();
		topMenuComponentPage.clickOnToysTab("Single Planes");
		productsPage.clickOnSelectedProductByName("AIRBUS");
		productPage.clickOnAddToWishListLink();
		int itemsAfter = myWishListPage.getWishListItemsCount();
		try {
			Assert.assertEquals(itemsBefore + 1, itemsAfter, "Number of items does not match");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Remove item from wighlist")
	public void tc03_removeItemFromWishListTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc03_removeItemFromWishListTest");
		topMenuComponentPage.clickOnToysTab("DOLLS");
		productsPage.clickOnSelectedProductByName("ASTRONAUT");
		String productPageName = productPage.getProductName();
		productPage.clickOnAddToWishListLink();
		int itemsBefore = myWishListPage.getWishListItemsCount();
		myWishListPage.removeItemByName("ASTRONAUT");
		int itemsAfter = myWishListPage.getWishListItemsCount();
		String toastString = myWishListPage.getPageToast();
		try {
			Assert.assertEquals(toastString, productPageName + " " + Utils.readProperty("myWishRemoveSusscessToast"));
			Assert.assertEquals(itemsBefore - 1, itemsAfter, "Number of items does not match");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Click on back button from empty wish list page")
	public void tc04_backFromEmptyWishListTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc04_backFromEmptyWishListTest");
		headerComponent.clickOnMyAccountButton();
		myAccountPage.clickOnMyaccountTab("My Wish List");
		myWishListPage.removeAllItemFromWishList();
		myWishListPage.clickOnWishListbackButton();
		String pageHeader = myAccountPage.getPageHeader();
		try {
			Assert.assertEquals(pageHeader, Utils.readProperty("MyAccountPageHeader"), "page header is not correct");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Click on back button from full wighlist page")
	public void tc07_backFromFullWishListTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc07_backFromFullWishListTest");
		topMenuComponentPage.clickOnToysTab("DOLLS");
		productsPage.clickOnSelectedProductByName("ASTRONAUT");
		productPage.clickOnAddToWishListLink();
		myWishListPage.clickOnBackButton();
		String pageHeader = myAccountPage.getPageHeader();
		try {
			Assert.assertEquals(pageHeader, Utils.readProperty("MyAccountPageHeader"), "Page header is not correct");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Add all items from wish list to cart")
	public void tc05_addAllWishListItemToCartTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc05_addAllWishListItemToCartTest");
		topMenuComponentPage.clickOnToysTab("HELICOPTERS");
		productsPage.clickOnSelectedProductByName("JETRANGER");
		productPage.clickOnAddToWishListLink();
		int sumBefore = myWishListPage.getSumOfAllItemsQty();
		headerComponent.openCart();
		int cartHeaderItemNumberBefore = headerComponent.getTopItemCount();
		headerComponent.closeCart();
		myWishListPage.clickOnAddAllToCartButton();
		headerComponent.openCart();
		int cartHeaderItemNumberAfter = headerComponent.getTopItemCount();
		headerComponent.closeCart();
		String emptypageString = myWishListPage.getEmptyPageToast();
		String toastString = myWishListPage.getPageToast();
		try {
			Assert.assertEquals(cartHeaderItemNumberBefore + sumBefore, cartHeaderItemNumberAfter,
					"Number of items does not match");
			Assert.assertTrue(toastString.contains(Utils.readProperty("myWishListAddAllToCart")),
					"Toast message is not displayed/correct");
			Assert.assertEquals(emptypageString, Utils.readProperty("emptyWishListText"), "Text is not correct");
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}

}
