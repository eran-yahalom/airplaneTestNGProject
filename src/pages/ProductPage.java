package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import components.*;
import utils.ExtentReportTest;
import annotations.MethodDescription;

public class ProductPage extends BasePage {

	Breadcrumbs breadcrumbs;
	String toastString = "You added";

	@FindBy(css = "div.product-sku")
	private WebElement productSku;

	@FindBy(css = "div.product-title")
	private WebElement productName;

	@FindBy(css = "[data-price-type='finalPrice']")
	private WebElement productPrice;

	@FindBy(css = "#qty")
	private WebElement productQty;

	@FindBy(css = "#product-addtocart-button")
	private WebElement addToCartButton;

	@FindBy(css = "a.action.towishlist")
	private WebElement addToWishListLink;

	@FindBy(css = "[role='alert']")
	private WebElement alertToast;

	@FindBy(css = "[role='alert'] a")
	private WebElement shoppingcartLink;

	public ProductPage(WebDriver driver) {
		super(driver);

	}

	@MethodDescription("Get product SKU")
	public String getProductSku() {
		logger.info("Getting product SKU");
		ExtentReportTest.getTest().info("Getting product SKU");
		try {
			String textString = getText(productSku);
			wait.until(ExpectedConditions.visibilityOf(productSku));
			return textString;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting product SKU");
			throw new RuntimeException(e);
		}
	}

	@MethodDescription("Get product name")
	public String getProductName() {
		logger.info("Getting product Name");
		ExtentReportTest.getTest().info("Getting product Name");
		try {
			wait.until(ExpectedConditions.visibilityOf(productName));
			return getText(productName);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting product name");
			throw new RuntimeException(e);
		}
	}

	@MethodDescription("Get product quantity")
	public String getProductQuantity() {
		logger.info("Getting product qty");
		ExtentReportTest.getTest().info("Get product quantity");
		try {
			String qtyString = productQty.getAttribute("value");
			return qtyString;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting product qty");
			throw new RuntimeException(e);
		}
	}

	@MethodDescription("Change product quantity")
	public void setProductQuantity(String numOfItems) {
		logger.info("Changing product qty");
		ExtentReportTest.getTest().info("Changing product qty");
		try {
			productQty.clear();
			fillText(productQty, numOfItems);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while changing product qty");
		}
	}

	@MethodDescription("Click on add to cart button")
	public void clickOnAddItemTocartButton() {
		logger.info("Click on add to cart button");
		ExtentReportTest.getTest().info("Click on add to cart button");
		try {
			click(addToCartButton);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on add to cart button");
		}
	}

	@MethodDescription("Check that success toast message is displayed")
	public boolean isSuccessToastDisplayed() {
		logger.info("Check that toast is displayed");
		ExtentReportTest.getTest().info("Check that toast is displayed");
		try {
			wait.until(ExpectedConditions.visibilityOf(alertToast));
			String toastString = getText(alertToast);
			String itemNameString = getProductName();
			String alert = "You added " + itemNameString + " to your shopping cart.";
			if (toastString.equals(alert)) {
				logger.info("Toast message is displayed");
				return true;
			}

			return false;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting success toast text");
		}
		return false;
	}

	@MethodDescription("Click on shopping cart link")
	public void clickOnShoppingCartLink() {
		logger.info("Clicking on shopping cart link");
		ExtentReportTest.getTest().info("Clicking on shopping cart link");
		try {
			click(shoppingcartLink);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on shopping cart link");
		}
	}

	@MethodDescription("Click odd to whishlist link")
	public void clickOnAddToWishListLink() {
		logger.info("Clicking on whish list link");
		ExtentReportTest.getTest().info("Clicking on whish list link");
		try {
			wait.until(ExpectedConditions.elementToBeClickable(addToWishListLink));
			click(addToWishListLink);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on add to wishlist link");
		}
	}
}
