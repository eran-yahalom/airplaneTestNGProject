package components;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.FindAll;
import annotations.MethodDescription;
import pages.BasePage;
import utils.ExtentReportTest;

/**************************
 * handles breadcrumbs
 **************************/
public class Breadcrumbs extends BasePage {

	@FindBy(css = ".items > li >a")
	private WebElement homeBreadcrumb;

	@FindAll({ @FindBy(css = ".breadcrumbs > ul.items > li:nth-child(3)"),
			@FindBy(css = ".breadcrumbs > ul.items > li:nth-child(2)") })
	private List<WebElement> ProductItem;

	@FindBy(css = ".item.home")
	private WebElement home;

	public Breadcrumbs(WebDriver driver) {
		super(driver);

	}

	@MethodDescription("Get last breadcrumb text")
	public String getBreadCrumbName() {
		logger.error("Getting last breadcrumb text");
		ExtentReportTest.getTest().info("etting last breadcrumb text");
		try {
			longWait.until(ExpectedConditions.elementToBeClickable(homeBreadcrumb));
			for (WebElement breadcrumb : ProductItem) {
				if (breadcrumb.isDisplayed()) {
					longWait.until(ExpectedConditions.elementToBeClickable(breadcrumb));
					return getText(breadcrumb);
				}
			}
			logger.warn("No visible breadcrumb found.");
			return null;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting last bredcrumb");
		}
		return null;
	}

}
