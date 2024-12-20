package components;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import annotations.MethodDescription;
import pages.BasePage;
import utils.ExtentReportTest;

/**************************
 * handles side bar components like: cart,search
 **************************/

public class TopMenuComponentPage extends BasePage {

	// red menu- toys tab
	@FindBy(css = "[href*='/toys/']:not([class*='level-top'])")
	private List<WebElement> toysSubMenu;

	@FindBy(css = "[href*='/toys/'][class*='level-top']")
	private WebElement toysMenuButton;

	// top red menu-7 tabs
	@FindBy(css = ".ui-menu-item.level0.fullwidth") // [class="level-top"]
	private List<WebElement> topMenu;

	public TopMenuComponentPage(WebDriver driver) {
		super(driver);

	}

	@MethodDescription("Click on the menu tab drop down item by item name")
	public void clickOnToysTab(String productName) {
		String nameString;
		waiting(2000);
		logger.info("Click on tab: " + productName);
		ExtentReportTest.getTest().info("Click on the menu tab drop down item by item name");
		hover(toysMenuButton);
		try {
			for (int i = 0; i < toysSubMenu.size(); i++) {
				nameString = getText(toysSubMenu.get(i));
				if (nameString.equals(productName.toUpperCase())) {
					click(toysSubMenu.get(i));
					break;
				}

			}
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"n exception occurred while searching for a dropdown name");
		}

	}
}
