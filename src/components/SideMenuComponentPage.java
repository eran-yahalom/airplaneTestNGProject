package components;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import annotations.MethodDescription;
import pages.BasePage;
import utils.ExtentReportTest;

/**************************
 * handles side bar components like: category ,brand
 **************************/

public class SideMenuComponentPage extends BasePage {

	@FindBy(css = "[attribute='license_description'] > div:nth-child(2)>ol>li")
	private List<WebElement> categoryArea;

	@FindBy(css = "[attribute='license_description']")
	private WebElement categorySelector;

	public SideMenuComponentPage(WebDriver driver) {
		super(driver);

	}

	@MethodDescription("çlick on selected category item checkbox and return number of items from header")
	public String findCategoryElement(String categoryToSelect) {
		logger.info("çlick on selected category item checkbox");
		ExtentReportTest.getTest().info("Click on selected category item checkbox");
		String catString;
		String count;
		WebElement cccElement = null;
		try {
			for (WebElement line : categoryArea) {
				catString = line.findElement(By.cssSelector("a")).getText();
				cccElement = line.findElement(By.cssSelector("a"));
				count = line.findElement(By.cssSelector(".count")).getText();
				if (catString.contains(categoryToSelect)) {
					cccElement.click();
					return count;
				}
			}
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while checking on select category checkbox");
		}
		return null;
	}

	@MethodDescription("Click on category header")
	public void clickOnCategoryDropDownButton() {
		logger.info("Click on category header");
		ExtentReportTest.getTest().info("Click on category header");
		click(categorySelector);
	}

	@MethodDescription("Return int number of items in sub category headeder")
	public int getLinkItemCount(String elem) {
		int count = 0;
		count = Integer.parseInt(elem.split("")[0]);
		logger.info("Number of items: " + count);
		ExtentReportTest.getTest().info("Count number of items in sub category headeder");
		return count;
	}

}
