package tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import java.util.Set;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pages.AddressBookPage;
import utils.ExtentReportTest;
import com.aventstack.extentreports.ExtentTest;
import pages.CustomerLoginPage;
import pages.HomePage;
import pages.MyAccountPage;
import pages.MyWishListPage;
import pages.PaymentMethodPage;
import pages.ProductPage;
import pages.ProductsPage;
import pages.ShippingAddressPage;
import components.BottomComponentPage;
import components.Breadcrumbs;
import components.HeaderComponentPage;
import components.SideMenuComponentPage;
import components.SortFilterAndPageItemsComponentPage;
import components.TopMenuComponentPage;
import pages.ShoppingCartPage;
import utils.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
//	  protected ExtentReports extentReports;
//	    protected ExtentTest test;
	protected WebDriver driver;

	protected CustomerLoginPage loginPage;
	protected ShoppingCartPage shoppingCartPage;
	protected BottomComponentPage bottomComponentPage;
	protected HeaderComponentPage headerComponent;
	protected SideMenuComponentPage sideMenuComponentPage;
	protected SortFilterAndPageItemsComponentPage sortFilterComponentPage;
	protected TopMenuComponentPage topMenuComponentPage;
	protected Breadcrumbs breadcrumbs;
	protected ProductsPage productsPage;
	protected HomePage homePage;
	protected ProductPage productPage;
	protected MyWishListPage myWishListPage;
	protected MyAccountPage myAccountPage;
	protected ShippingAddressPage shippingAddressPage;
	protected PaymentMethodPage paymentMethodPage;
	protected AddressBookPage addressBookPage;
	ExtentReports extent = new ExtentReports();

	@BeforeClass
	public void setUp() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + " Setup");

		// Setup WebDriver
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);

		// Maximize the browser window
		driver.manage().window().maximize();

		// Load the URL and set timeouts
		driver.get(Utils.readProperty("url"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));

		// Add session cookie for authentication
		Cookie sessionCookie = new Cookie("PHPSESSID", "shq192ner34gs80k257e9bylc"); // Replace with the full value
		driver.manage().addCookie(sessionCookie);
		driver.navigate().refresh();

		// Initialize page objects
		initializePageObjects();

		// Handle system popups and login
		handlePopupsAndLogin();
	}

	private void initializePageObjects() {
		loginPage = new CustomerLoginPage(driver);
		shoppingCartPage = new ShoppingCartPage(driver);
		headerComponent = new HeaderComponentPage(driver);
		bottomComponentPage = new BottomComponentPage(driver);
		sideMenuComponentPage = new SideMenuComponentPage(driver);
		sortFilterComponentPage = new SortFilterAndPageItemsComponentPage(driver);
		topMenuComponentPage = new TopMenuComponentPage(driver);
		breadcrumbs = new Breadcrumbs(driver);
		productsPage = new ProductsPage(driver);
		homePage = new HomePage(driver);
		productPage = new ProductPage(driver);
		myWishListPage = new MyWishListPage(driver);
		myAccountPage = new MyAccountPage(driver);
		shippingAddressPage = new ShippingAddressPage(driver);
		paymentMethodPage = new PaymentMethodPage(driver);
		addressBookPage = new AddressBookPage(driver);
	}

	private void handlePopupsAndLogin() {
		Utils.cleanUpScreenshotsFolder();
		Utils.closeSystemPopUps(driver, ".z-title a");

		ExtentReportTest.getTest().info("Starting login function");
		loginPage.login(Utils.readProperty("user1"), Utils.readProperty("password1"));
		ExtentReportTest.getTest().info("Finishing login function");

		headerComponent.cleanCart();
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit(); // Close the browser instance
		}
		ExtentReportTest.flush();
	}

	@AfterMethod
	public void afterMethod() {
		// Log that the test execution is completed
		ExtentReportTest.getTest().info("Test execution completed");
	}

	@AfterMethod
	public void failedTest(ITestResult result) {
		// check if the test failed
		if (result.getStatus() == ITestResult.FAILURE) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File srcFile = ts.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile, new File("./ScreenShots/" + result.getName() + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
