package utils;

import org.openqa.selenium.WebDriver;
import java.awt.Desktop;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.dockerjava.api.model.Driver;
import com.github.javafaker.Faker;

import annotations.MethodDescription;
import io.netty.handler.timeout.TimeoutException;
import pages.BasePage;
import pages.ProductsPage;
import tests.BaseTest;

public class Utils extends BasePage {
//	private static final Logger logger = LoggerUtils.log(Utils.class);
	static Faker faker = new Faker();
	// Generate a random name
	static WebDriverWait longWait;
	private WebDriver driver;

	public Utils(WebDriver driver) {
		super(driver);
		longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
		// TODO Auto-generated constructor stub
	}

//    // Constructor that accepts WebDriver as a parameter
//    public Utils() {
//    	 this.driver = new ChromeDriver();
//    }

	public static String readProperty(String key) {

		String value = "";
		try (InputStream input = new FileInputStream("./src/data/configuration.properties")) {
			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			value = prop.getProperty(key);

		} catch (Exception e) {

		}
		return value;
	}

	public static void cleanUpScreenshotsFolder() {
		File screenshotDir = new File("./ScreenShots");
		if (screenshotDir.exists()) {
			File[] files = screenshotDir.listFiles();
			if (files != null) { // Check if the directory contains any files
				for (File file : files) {
					if (file.isFile()) { // Ensure it's a file, not a directory
						file.delete();
					}
				}
			}
		}
		// Simulate F5 key press
	}

	@MethodDescription("Convert string currency to double[for pricing- also remove creency icon]")
	public static double parseCurrencyStringToDouble(String currencyString) {
		currencyString = currencyString.replaceAll("[^\\d.]", "");
		return Double.parseDouble(currencyString);
	}

	@MethodDescription("Multipling price formatter")
	public static double multiplyPrice(double price, String multiplierString) {
		// Parse the multiplier string to an integer
		int multiplier = Integer.parseInt(multiplierString);

		// Multiply the price by the multiplier
		return price * multiplier;
	}

	@MethodDescription("Convert string to int [number of items]")
	public static int convertStringToInt(String input) {
		try {
			// Trim the string to remove any leading or trailing whitespace
			return Integer.parseInt(input.trim());
		} catch (NumberFormatException e) {
			System.err.println("Invalid input: " + input + " is not a valid integer.");
			throw e;
		}
	}

	@MethodDescription(" Generate a random first name")
	public static String getFirstName() {
		return faker.name().firstName();
	}

	@MethodDescription(" Generate a random last name")
	public static String getLastName() {
		return faker.name().lastName();
	}

	@MethodDescription(" Generate a random company name")
	public static String getCompanyName() {
		return faker.company().name();
	}

	@MethodDescription(" Generate a random address")
	public static String getAddress() {
		return faker.address().streetAddress();
	}

	@MethodDescription(" Generate a random state")
	public static String getState() {
		return faker.address().state();
	}

	@MethodDescription(" Generate a random city")
	public static String getCity() {
		return faker.address().city();
	}

	@MethodDescription(" Generate a random zip code")
	public static String getZipCode() {
		return faker.number().digits(6);
	}

	@MethodDescription(" Generate a random mobile phone number")
	public static String getMobilePhoneNumber() {
		return faker.phoneNumber().cellPhone();
	}

	@MethodDescription("Close system pop ups")
	public static void closeSystemPopup(WebDriver driver, List<WebElement> popupCloseButtons) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(5))
					.until(ExpectedConditions.or(ExpectedConditions.visibilityOfAllElements(popupCloseButtons),
							ExpectedConditions.invisibilityOfAllElements(popupCloseButtons)));

			for (WebElement closeButton : popupCloseButtons) {
				if (closeButton.isDisplayed()) {
					closeButton.click();
					System.out.println("popup closed.");
					break;
				}
			}
		} catch (NoSuchElementException | TimeoutException e) {
			System.out.println("No welcome popup found, continuing with the test.");
		}
	}

	@MethodDescription("Remove all items")
	public static void removeAllItems(WebDriver driver, List<WebElement> element, String deleteSelector,
			String confirmSelector, boolean withConfirmation) {
		try {
			while (true) {
				if (element.isEmpty()) {
					System.out.println("No items in page.");
					break;
				}

				WebElement product = element.get(0);
				product.findElement(By.cssSelector(deleteSelector)).click();
				if (withConfirmation) {
					driver.findElement(By.cssSelector(confirmSelector)).click();
				}
			}
		} catch (NoSuchElementException | NumberFormatException e) {
			System.out.println("Can't find items on the page.");
		} catch (StaleElementReferenceException e) {
			System.out.println("Encountered a stale element reference exception.");
		}
	}

	@MethodDescription("Approve and close the delete pop up")
	public static void approveDeleteItemPopUp(WebElement element) {
		longWait.until(ExpectedConditions.visibilityOf(element));
		element.click();
	}

	public static void waitForLoaderToBeRemoved(WebDriver driver, String loaderCss, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			logger.info("Loader is visible");
			// Wait for the loader to appear (if present)
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(loaderCss)));
			logger.info("wait for loader to be removed");
			// Wait for the loader's style attribute to indicate it is removed
			wait.until(ExpectedConditions.attributeToBe(By.cssSelector(loaderCss), "style", "display: none;"));
			logger.info("Loader is removed");
		} catch (Exception e) {
			System.out.println("Loader was not found or already removed: " + e.getMessage());
		}
	}

	public static void waitForLoaderToDisappear(WebDriver driver, By loaderLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));// Wait for up to 3 seconds

		while (true) {
			try {
				// Check if loader is displayed
				WebElement loader = driver.findElement(loaderLocator);

				if (loader.isDisplayed()) {
					System.out.println("Loader is displayed. Waiting for it to disappear...");

					// Wait for the loader to disappear
					wait.until(ExpectedConditions.invisibilityOf(loader));
					System.out.println("Loader disappeared. Exiting wait loop.");
					break;
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignored) {
				}
			} catch (org.openqa.selenium.NoSuchElementException e) {
				// Loader not found, continue checking
				System.out.println("Loader not found. Retrying...");
				wait.until(ExpectedConditions.presenceOfElementLocated(loaderLocator));
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
				// Handle stale element by refetching the element
				System.out.println("Stale element encountered. Retrying...");
			}
		}
	}

//	public static void waitForElementToBeGone(WebDriver driver, WebElement element, Duration i) {
//		if (element.isDisplayed()) {
//			logger.info("Loader is visible");
//			new WebDriverWait(driver, i).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
//		}
//	}

	public static void closeSystemPopUps(WebDriver driver, String cssSelector) {
		if (driver == null) {
			throw new IllegalArgumentException("WebDriver instance cannot be null");
		}
		try {
			List<WebElement> popUpElements = driver.findElements(By.cssSelector(cssSelector));
			if (!popUpElements.isEmpty() && popUpElements.get(0).isDisplayed()) {
				popUpElements.get(0).click();
				logger.info("System pop-up closed successfully.");
			} else {
				logger.info("No visible pop-up to close.");
			}
		} catch (Exception e) {
			ExtentReportTest.getTest().fail("Error while attempting to close system pop-up: " + e.getMessage());

		}
	}

	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		return sw.toString();
	}

}
