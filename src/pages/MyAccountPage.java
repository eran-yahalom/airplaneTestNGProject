package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import annotations.MethodDescription;
import utils.ExtentReportTest;
import utils.Utils;

public class MyAccountPage extends BasePage {

	@FindBy(css = ".page-title span")
	private WebElement pageHeader;

	@FindBy(css = ".message-success.success.message")
	private WebElement toast;

	@FindBy(css = "[class='nav item'] a")
	private List<WebElement> tabArea;

	@FindBy(css = "a.action.edit")
	private WebElement editButton;

	@FindBy(css = "[class^='box box']")
	private List<WebElement> boxArea;

	@FindBy(css = "#street_1")
	private WebElement streetAddress;

	@FindBy(css = "#city")
	private WebElement city;

	@FindBy(css = "#zip")
	private WebElement zipCode;

	@FindBy(css = "[name='country_id']")
	private WebElement country;

	@FindBy(css = "[class='action save primary']")
	private WebElement saveAddressButton;

	// edit box
	@FindBy(css = "a[class='action edit']")
	private WebElement editLink;

	// box text
	@FindBy(css = "div.box-content>p")
	private WebElement boxContent;

	// Account Information

	public MyAccountPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@MethodDescription("Get page header")
	public String getPageHeader() {
		return getText(pageHeader);
	}

	@MethodDescription("Click on a specific tab")
	public void clickOnMyaccountTab(String tabname) {
		for (WebElement tab : tabArea) {
			if (getText(tab).equalsIgnoreCase(tabname)) {
				click(tab);
				break;
			}
		}
	}

	@MethodDescription("Click on default billing address link")
	public void clickOnDefaultBillingAddressEdit() {
		logger.info("Click on default billing address link");
		ExtentReportTest.getTest().info("Click on default billing address link");
		try {
			boxArea.get(3).findElement(By.cssSelector("a.action.edit")).click();
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on default billing address link");
		}
	}

	// check that we can add method to utils/base
	@MethodDescription("Select country")
	public void selectCountry(String selectString) {
		logger.info("Starting handeling dropDown");
		longWait.until(ExpectedConditions.elementToBeClickable(country));
		Select select = new Select(country);
		select.selectByValue(selectString);

	}

	@MethodDescription("edit driver address")
	public void editAddress(String countryName, String region) {
		longWait.until(ExpectedConditions.visibilityOf(streetAddress));
		logger.info("Entering address");
		fillText(streetAddress, Utils.getAddress());
		fillText(city, Utils.getCity());
		fillText(zipCode, Utils.getZipCode());
		selectDropDownHandler(country, countryName);
		click(saveAddressButton);
	}

	@MethodDescription("Get toast message")
	public String getPageToastMessage() {
		logger.info("Get toast message");
		ExtentReportTest.getTest().info("Get toast message");
		try {
			longWait.until(ExpectedConditions.visibilityOf(toast));
			logger.info("Catching toast message");
			return getToastMessage(toast);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting toast message");
		}
		return null;
	}

	@MethodDescription("Get zip code")
	public String getPageZipCode() {
		logger.info("Get zip code");
		ExtentReportTest.getTest().info("Get zip code");
		try {
			String zipString;
			longWait.until(ExpectedConditions.visibilityOf(zipCode));
			waiting(2000);
			zipString = zipCode.getAttribute("value");
			logger.info("Zip code from edit page is: " + zipString);
			return zipString;
		} catch (Exception e) {

			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting zip code");
			throw e;
		}
	}
}
