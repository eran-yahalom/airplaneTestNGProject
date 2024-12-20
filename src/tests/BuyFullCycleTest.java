package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import components.HeaderComponentPage;
import components.TopMenuComponentPage;
import pages.CustomerLoginPage;
import pages.PaymentMethodPage;
import pages.ProductsPage;
import pages.ShippingAddressPage;
import utils.ExtentReportTest;
import utils.Utils;

public class BuyFullCycleTest extends BaseTest {
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
		topMenuComponentPage = new TopMenuComponentPage(incognitoDriver);
		productsPage = new ProductsPage(incognitoDriver);
		shippingAddressPage = new ShippingAddressPage(incognitoDriver);
		paymentMethodPage = new PaymentMethodPage(incognitoDriver);
		loginPage.login(Utils.readProperty("user"), Utils.readProperty("password"));
		headerComponent.openCart();
		headerComponent.removeAllItemFromCart();
	}

	@Test(description = "Buy item- invalid credit card ")
	public void tc01_invalidCreditCardTest() {
		 ExtentReportTest.createTest(this.getClass().getSimpleName() +
		 "-tc01_invalidCreditCardTest");
		topMenuComponentPage.clickOnToysTab("Single Planes");
		productsPage.addRandomProductToCart();
		headerComponent.openCart();
		headerComponent.clickOnGoToCheckOutButton();
		shippingAddressPage.addAShippingAddress("IL", null);
		String toastString = paymentMethodPage.fillInCreditcard(Utils.readProperty("BT_cardNumber"),
				Utils.readProperty("BT_expiry"), Utils.readProperty("BT_CVV"));
		try {
			Assert.assertEquals(toastString, Utils.readProperty("reCaptchaErrorThreshold"), "Cant see toast");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}
}
