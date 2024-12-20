package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import annotations.MethodDescription;
import utils.ExtentReportTest;

public class HomePage extends BasePage {

	@FindBy(css = "#featured_product>div>div> .item.product.product-item")
	private List<WebElement> featuredProducts;

	@FindBy(css = "#new_product>div>div> .item.product.product-item")
	private List<WebElement> newProducts;

	@FindBy(css = ".col-sm-12 .filterproduct-title >span >strong")
	private List<WebElement> productNameHeaders;

	@FindBy(css = ".image-link")
	private List<WebElement> imageLinks;

	public HomePage(WebDriver driver) {
		super(driver);

	}

	@MethodDescription("Get Featured Products text")
	public String getFeaturedProductsText() {
		logger.info("Get Featured Products text");
		ExtentReportTest.getTest().info("Get Featured Products text");
		try {
			String productTextString;
			wait.until(ExpectedConditions.visibilityOf(productNameHeaders.get(0)));
			productTextString = getText(productNameHeaders.get(0));
			return productTextString;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting featured product text");
		}
		return null;
	}

}
