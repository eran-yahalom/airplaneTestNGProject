package components;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import annotations.MethodDescription;
import pages.BasePage;
import utils.ExtentReportTest;

/**************************
 * handles sort and filter components
 **************************/

public class SortFilterAndPageItemsComponentPage extends BasePage {
	HeaderComponentPage headerComponent;
	// Products>product area- same in every page

	// show sort
	@FindBy(css = "#limiter-top")
	private WebElement topShowFilter;

	@FindBy(css = "select#sorter-top[class='sorter-options']")
	private WebElement topSort;

	// ROW
	@FindBy(css = ".products.wrapper.list.products-list .list-wrapper tbody tr")
	private List<WebElement> rowArea;

	@FindBy(css = "a#mode-grid-top")
	private WebElement gridButton;

	@FindBy(css = "a#mode-list-top")
	private WebElement listButton;

	public SortFilterAndPageItemsComponentPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@MethodDescription("Use top 'Show' filter to change number of items in page")
	public void filterByNumberOfItems(String selectString) {
		logger.info("Use top 'Show' filter to change number of items in page");
		ExtentReportTest.getTest().info("Use top 'Show' filter to change number of items in page");
		try {
			longWait.until(ExpectedConditions.elementToBeClickable(topShowFilter));
			Select select = new Select(topShowFilter);
			select.selectByValue(selectString);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while trying to change number of page items");
		}

	}

	@MethodDescription("Use 'Sort by' sorter")
	public void sortBy(String selectString) {
		logger.info("Opening sort");
		ExtentReportTest.getTest().info("Opening sort");
		try {
			longWait.until(ExpectedConditions.elementToBeClickable(topSort));
			Select select = new Select(topSort);
			select.selectByValue(selectString);
			wait.until(ExpectedConditions.elementToBeClickable(topSort));
			waiting(5000);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on sort");
		}
	}

	@MethodDescription("Click on list button")
	public void clickOnListButton() {
		logger.info("Click on list button");
		ExtentReportTest.getTest().info("Click on list buttont");
		try {
			longWait.until(ExpectedConditions.elementToBeClickable(listButton));
			click(listButton);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on list button");
		}
	}

	@MethodDescription("Click on grid button")
	public void clickOnGridButton() {
		logger.info("Click on grid button");
		ExtentReportTest.getTest().info("Click on grid button");
		try {
			click(gridButton);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on grid button");
		}

	}

	@MethodDescription("Counnt number of rows")
	public int countItemsInList() {
		logger.info("Counnt number of rows");
		ExtentReportTest.getTest().info("Counnt number of rows");
		try {
			int rowNumber = rowArea.size();
			logger.info("Number of rows is: " + rowNumber);
			return rowNumber;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while counting items in list");
		}
		return 0;

	}

}
