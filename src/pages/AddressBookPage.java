package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import annotations.MethodDescription;
import components.HeaderComponentPage;
import utils.ExtentReportTest;
import utils.Utils;

public class AddressBookPage extends BasePage {
	private HeaderComponentPage headerComponentPage;

	@FindBy(css = "[class^='box box']")
	private List<WebElement> boxArea;

	@FindBy(css = "[title='Add New Address']")
	private WebElement addNewAddressButton;

	@FindBy(css = "a[class='action back']")
	private WebElement backButton;

	@FindBy(css = "#additional-addresses-table>tbody>tr")
	private List<WebElement> additionalAddressEntries;

	public AddressBookPage(WebDriver driver) {
		super(driver);
		headerComponentPage = new HeaderComponentPage(driver);
	}

	@MethodDescription("Click on change billing address link")
	public void clickOnChangeBillingAddressLink() {
		logger.info("Click on change billing address link");
		ExtentReportTest.getTest().info("Click on change billing address link");
		try {
			boxArea.get(0).findElement(By.cssSelector("a.action.edit")).click();
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on change billing address");
		}
	}

	@MethodDescription("Click on change shipping address link")
	public void clickOnChangeShippingAddressLink() {
		logger.info("Click on change shipping address link");
		ExtentReportTest.getTest().info("Click on change shipping address link");
		try {
			waiting(8000);
			boxArea.get(1).findElement(By.cssSelector("a.action.edit")).click();
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on change shipping address link");
		}
	}

	@MethodDescription("Click on back button")
	public void clickOnBackButton() {
		logger.info("Click on back button");
		ExtentReportTest.getTest().info("Click on back button");
		try {
			click(backButton);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on back button");
		}
	}

	@MethodDescription("Click on add new address button")
	public void clickOnAddNewAddressButton() {
		logger.info("Click on add new address button");
		ExtentReportTest.getTest().info("Click on add new address button");
		try {
			wait.until(ExpectedConditions.visibilityOf(addNewAddressButton));
			click(addNewAddressButton);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on add new address button");
		}
	}

	@MethodDescription("Counting additional addresses in 'Additional Address Entries' table")
	public int getAdditionalAddressEntriesCount() {
		logger.info("Counting additional addresses");
		ExtentReportTest.getTest().info("Counting additional addresses");
		try {
			int count = additionalAddressEntries.size();
			logger.info("number of items: " + count);
			return count;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while Counting additional addresses");
		}
		return 0;
	}

	@MethodDescription("Delete first entry in 'Additional Address Entries' table")
	public void deleteFirstEntryInTable() {
		logger.info("Deleting entry in 'Additional Address Entries' table");
		ExtentReportTest.getTest().info("Deleting entry in 'Additional Address Entries' table");
		try {
			click(additionalAddressEntries.get(0).findElement(By.cssSelector("a[role='delete-address']")));
			headerComponentPage.approveDeleteItemPopUp();
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while Deleting entry in 'Additional Address Entries'");
		}
	}

	@MethodDescription("Get first zipCode from  'Additional Address Entries' table")
	public String getFirstZipCodeEntryInTable() {
		logger.info("Get first zipCode from table");
		ExtentReportTest.getTest().info("Get first zipCode from table");
		try {
			String zipCodeString = getText(additionalAddressEntries.get(0).findElement(By.cssSelector(".col.zip")));
			return zipCodeString;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting first zip code'");
		}
		return null;

	}

	@MethodDescription("Click on first entry edit link")
	public void clickOnEditEntryInTable() {
		logger.info("Click on first entry edit link");
		ExtentReportTest.getTest().info("Click on first entry edit link");
		try {
			click(additionalAddressEntries.get(0).findElement(By.cssSelector("a.action.edit")));
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on first entry link'");
		}

	}

	@MethodDescription("Remove all address tabs")
	public void removeAlltableItems() {
		logger.info("Remove all address tabs");
		ExtentReportTest.getTest().info("Remove all address tabs");
		try {
			longWait.until(ExpectedConditions.visibilityOf(additionalAddressEntries.get(0)));
			Utils.removeAllItems(driver, additionalAddressEntries, "a[role='delete-address']",
					".action-primary.action-accept", true);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while Remove all address tabs'");
		}

	}

}
