package pages;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Set;
import org.apache.logging.log4j.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import annotations.MethodDescription;
import io.netty.handler.timeout.TimeoutException;
import utils.ExtentReportTest;

public class BasePage {
	public static Logger logger = LogManager.getLogger(BasePage.class.getName());
	protected WebDriver driver;
	public WebDriverWait wait;
	protected WebDriverWait longWait;
	protected WebDriverWait socialNetworkWait;
	static String mainWindow;
	JavascriptExecutor js;

	public BasePage(WebDriver driver) {
		super();
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // check if need it here!!!
		longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
		socialNetworkWait = new WebDriverWait(driver, Duration.ofSeconds(180));
		js = (JavascriptExecutor) driver;
	}

	@MethodDescription("Filling text in field")
	public void fillText(WebElement e1, String text) {
		e1.clear();
		highlightElement(e1, "blue");
		e1.sendKeys(text);
	}

	@MethodDescription("Click on element")
	public void click(WebElement e1) {
		longWait.until(ExpectedConditions.elementToBeClickable(e1));
		highlightElement(e1, "black");
		e1.click();
	}

	@MethodDescription("Wait method")
	public void waiting(long milli) {
		try {
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@MethodDescription("Get element text")
	public String getText(WebElement el) {
		String text;
		int retries = 3; // Number of retries
		while (retries > 0) {
			try {
				text = el.getText();
				logger.info("Element text: " + text);
				return text;
			} catch (StaleElementReferenceException e) {
				logger.warn("Encountered StaleElementReferenceException. Retrying...");
				retries--;
				waitUntilVisible(el); // Ensure the element is still visible
			}
		}
		throw new RuntimeException("Failed to retrieve text due to stale element after retries.");
	}

	@MethodDescription("Wait until element is visible")
	private void waitUntilVisible(WebElement el) {
		wait.until(ExpectedConditions.visibilityOf(el));
	}

	@MethodDescription("Go from main tab to second chrome tab")
	public void moveToNewWindow() {
		mainWindow = driver.getWindowHandle();
		Set<String> list = driver.getWindowHandles();
		for (String win : list) {
			driver.switchTo().window(win);
		}
	}

	@MethodDescription("Go back from second chrome tab to main tab")
	public void backToMainWindow() {
		ExtentReportTest.getTest().info("Moving back to main page");
		driver.close();
		driver.switchTo().window(mainWindow);
	}

	@MethodDescription("Wait for element to be visible")
	public void waitForElementToBeVisible(WebElement el) {
		wait.until(ExpectedConditions.visibilityOf(el));
	}

	@MethodDescription("Check if element is displayed in page")
	public boolean isDisplayed(WebElement e1) {
		try {
			return e1 != null && e1.isDisplayed();
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			return false;
		} catch (Exception e) {
			logger.info("Unexpected exception while checking element visibility: " + e.getMessage());
			return false;
		}
	}

	@MethodDescription("Highlighting a specific element in page")
	protected void highlightElement(WebElement e1, String color) {
		// keep the old style to change it back
		String originalStyle = e1.getAttribute("style");
		String newStyle = "border: 1px solid " + color + ";" + "background-color:yellow" + originalStyle + ";";
		// JavascriptExecutor js = (JavascriptExecutor) driver;

		// Change the style
		js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '"
				+ newStyle + "');},0);", e1);

		// Change the style back after few miliseconds
		js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '"
				+ originalStyle + "');},400);", e1);

	}

	@MethodDescription("Get text from element")
	public String getTextIfVisible(WebElement e1) {
		if (e1 != null && e1.isDisplayed()) {
			logger.info("Get text from element: " + e1.getText());
			return e1.getText();
		}
		return null;
	}

	@MethodDescription("Hover on element")
	public void hover(WebElement e1) {
		Actions actions = new Actions(driver);
		actions.moveToElement(e1).perform();
		waiting(3000);
	}

	@MethodDescription("Close system pop ups")
	public void closeSystemPopupIfPresent() {
		WebElement closeButton;
		try {
			new WebDriverWait(driver, Duration.ofSeconds(5)).until(
					ExpectedConditions.or(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".z-close")),
							ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".z-close"))));
			closeButton = driver.findElement(By.cssSelector(".z-close"));
			if (closeButton.isDisplayed()) {
				closeButton.click();
				logger.info("Welcome popup closed.");
			}
		} catch (NoSuchElementException | TimeoutException e) {
			logger.error("No welcome popup found, continuing with the test.");
		}
	}

	@MethodDescription("Get toast message")
	public String getToastMessage(WebElement element) {
		String message = null;
		try {
			message = getText(element);
			logger.info("Toast message is: " + message);
		} catch (NoSuchElementException | TimeoutException e) {
			logger.error("Toast message was not found");

		}
		return message;
	}

	@MethodDescription("Select dropdown handler")
	public void selectDropDownHandler(WebElement element, String selectString) {
		logger.info("Click on dropdown");
		longWait.until(ExpectedConditions.elementToBeClickable(element));
		Select select = new Select(element);
		select.selectByValue(selectString);

	}

	public static void printToLogAndHTML(String message, Logger logger) {
		logger.info(message);

		// Log to HTML report
		ExtentTest test = ExtentReportTest.getTest();
		if (test != null) {
			test.info(message);
		}
	}

}
