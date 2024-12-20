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

public class AddressBookTest extends BaseTest {
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

	/** All 7 tests run with success **/

	@Test(description = "Update billing address from Address Book page")
	public void tc02_updateBillingAddressMyAddressBookTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc02_updateBillingAddressMyAddressBookTest");
		headerComponent.clickOnMyAccountButton();
		myAccountPage.clickOnMyaccountTab("Address Book");
		addressBookPage.clickOnChangeBillingAddressLink();
		myAccountPage.editAddress("IL", null);
		String toastString = myAccountPage.getPageToastMessage();
		try {
			Assert.assertEquals(toastString, Utils.readProperty("saveAddressSuccessToast"), "Toast is not correct");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Update shipping address from Address Book page")
	public void tc01_updateShippingAddresssMyAddressBookTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc01_updateShippingAddresssMyAddressBookTest");
		headerComponent.clickOnMyAccountButton();
		myAccountPage.clickOnMyaccountTab("Address Book");
		addressBookPage.clickOnChangeShippingAddressLink();
		myAccountPage.editAddress("IL", null);
		String toastString = myAccountPage.getPageToastMessage();
		try {
			Assert.assertEquals(toastString, Utils.readProperty("saveAddressSuccessToast"), "Toast is not correct");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}

	@Test(description = "Add new address- from 'add new address' button")
	public void tc03_addNewAddressMyAddressBookTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc03_addNewAddressMyAddressBookTest");
		headerComponent.clickOnMyAccountButton();
		myAccountPage.clickOnMyaccountTab("Address Book");
		addressBookPage.clickOnAddNewAddressButton();
		myAccountPage.editAddress("IL", null);
		String toastString = myAccountPage.getPageToastMessage();
		try {
			Assert.assertEquals(toastString, Utils.readProperty("saveAddressSuccessToast"), "Toast is not correct");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}

	@Test(description = "Go back to My Account page")
	public void tc04_goBackToMyAccountTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc05_deleteTableEntryTest");
		headerComponent.clickOnMyAccountButton();
		myAccountPage.clickOnMyaccountTab("Address Book");
		addressBookPage.clickOnBackButton();
		String header = myAccountPage.getPageHeader();
		try {
			Assert.assertEquals(header, Utils.readProperty("MyAccountPageHeader"), "cant see my account page");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}

	@Test(description = "Deleting first entry in 'Additional Address Entries' table")
	public void tc05_deleteTableEntryTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc05_deleteTableEntryTest");
		headerComponent.clickOnMyAccountButton();
		myAccountPage.clickOnMyaccountTab("Address Book");
		addressBookPage.clickOnAddNewAddressButton();
		myAccountPage.editAddress("IL", null);
		int itemsBefore = addressBookPage.getAdditionalAddressEntriesCount();
		addressBookPage.deleteFirstEntryInTable();
		int itemsAfter = addressBookPage.getAdditionalAddressEntriesCount();
		try {
			Assert.assertEquals(itemsBefore, itemsAfter + 1, "Number of items is not correct");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}

	@Test(description = "Click on edit- first entry in 'Additional Address Entries' table")
	public void tc06_editTableEntryTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc06_editTableEntryTest");
		headerComponent.clickOnMyAccountButton();
		myAccountPage.clickOnMyaccountTab("Address Book");
		addressBookPage.clickOnAddNewAddressButton();
		myAccountPage.editAddress("IL", null);
		String zipCodeInTable = addressBookPage.getFirstZipCodeEntryInTable();
		addressBookPage.clickOnEditEntryInTable();
		String zipCodeFromEditPage = myAccountPage.getPageZipCode();
		String pageHeader = myAccountPage.getPageHeader();
		Assert.assertEquals(zipCodeInTable, zipCodeFromEditPage);
		try {
			Assert.assertEquals(pageHeader, Utils.readProperty("EditAddressPageHeader"), "Page header is not correct");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}

	@Test(description = "Remove all table items")
	public void tc07_removeAllTableEntryTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc07_removeAllTableEntryTest");
		headerComponent.clickOnMyAccountButton();
		myAccountPage.clickOnMyaccountTab("Address Book");
		addressBookPage.removeAlltableItems();
		int itemsAfter = addressBookPage.getAdditionalAddressEntriesCount();
		try {
			Assert.assertEquals(itemsAfter, 0, "Number of items is not correct");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}
}
