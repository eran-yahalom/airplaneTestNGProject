package tests;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import components.HeaderComponentPage;
import components.TopMenuComponentPage;
import pages.CustomerLoginPage;
import pages.MyAccountPage;
import pages.MyWishListPage;
import pages.ProductPage;
import pages.ProductsPage;
import utils.ExtentReportTest;
import utils.Utils;

public class MyAccountTest extends BaseTest {
	
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

	/* 1 test */
	/* fail - not find success toast(no toast in page) */
	@Test(description = "Update billing address from My Account page")
	public void tc01_updateAddressMyAccountTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc01_updateAddressMyAccountTest");
		headerComponent.clickOnMyAccountButton();
		myAccountPage.clickOnDefaultBillingAddressEdit();
		myAccountPage.editAddress("IL", null);
		String toastString = myAccountPage.getPageToastMessage();
		try {
			Assert.assertEquals(toastString, Utils.readProperty("saveAddressSuccessToast"),
					"Toast message is not correct");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}
}
