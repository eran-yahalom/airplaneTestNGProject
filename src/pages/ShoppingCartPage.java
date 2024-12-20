package pages;

import java.util.List;
import java.util.NoSuchElementException;
import utils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import annotations.MethodDescription;

public class ShoppingCartPage extends BasePage {

	// page header
	@FindBy(css = ".page-title")
	private WebElement header;

	// cart page summery box
	@FindBy(css = "div.cart-summary")
	private List<WebElement> cartPageSummery;

	@FindBy(css = ".summary.title.item-count")
	private WebElement orderSummeryItems;

	@FindBy(css = ".totals.sub [class='price']")
	private WebElement subTotalAmount;

	@FindBy(css = ".grand.totals.incl [class='price']")
	private WebElement orderTotalInclTaxAmount;

	@FindBy(css = ".grand.totals.excl [class='price']")
	private WebElement orderTotalExclTaxAmount;

	@FindBy(css = "[data-role='proceed-to-checkout']")
	private WebElement goToCheckOutButton;

	// cart items
	@FindBy(css = ".cart.item")
	private List<WebElement> cartArea;

	@FindBy(css = "div.product.sku a")
	private WebElement productSkuLink;

	@FindBy(css = ".product-item-name a")
	private WebElement productNameLink;

	@FindBy(css = ".col.subtotal>span>span [class='price']")
	private WebElement subtotal;

	@FindBy(css = ".col.price>span >span [class='price']")
	private WebElement price;

	@FindBy(css = "[data-role='cart-item-qty']")
	private WebElement qty;

	@FindBy(css = ".product-item-remove a")
	private WebElement removeLink;

	@FindBy(css = "[title='Update Cart']")
	private WebElement updateCartButton;

	// Apply Gift Cards
	@FindBy(css = "#giftcard_code")
	private WebElement applyGiftCardField;

	@FindBy(css = ".gift-card-apply >form >div >div:nth-child(2)>div >button:nth-child(1)")
	private WebElement giftCardApplyButton;

	@FindBy(css = "#coupon_code")
	private WebElement applyDiscountCodeField;

	@FindBy(css = "[value='Apply Discount']")
	private WebElement applyDiscountCodeButton;

	// coupon error messages

	@FindBy(css = "[data-ui-id='checkout-cart-validationmessages-message-error']")
	private WebElement applyGiftCardErrorMessage;

	@FindBy(css = ".message-error.error.message")
	private WebElement applyDiscountCodeErrorMessage;

	@FindBy(css = ".message-error.error.message")
	private WebElement generalErrorMessage;
	// empty cart text
	@FindBy(css = "div.cart-empty")
	private WebElement emptyCartText;

	@FindBy(css = "div.cart-empty a")
	private WebElement hereLink;

	public ShoppingCartPage(WebDriver driver) {
		super(driver);

	}

	@MethodDescription("Click on update cart button")
	public void clickOnUpdatecartButton() {
		logger.info("Click on update cart button");
		ExtentReportTest.getTest().info("Click on update cart button");
		try {
			click(updateCartButton);
			waiting(8000);
			longWait.until(ExpectedConditions.visibilityOf(subTotalAmount));
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while clicking on update cart button");
		}

	}

	@MethodDescription("Get number of items in cart")
	public int getNumberOfItemsInShoppingCart() {
		logger.info("Get number of items in cart");
		ExtentReportTest.getTest().info("Get number of items in cart");
		int numOfItems = 0;
		String numberString;
		try {
			for (WebElement element : cartArea) {
				numberString = element.findElement(By.cssSelector("[title='Qty']")).getAttribute("value");
				int number = Integer.parseInt(numberString);
				numOfItems += number;
				logger.info("Number of items in cart: " + numOfItems);
			}
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred whilegetting shopping cart icons");
		}
		return numOfItems;
	}

	@MethodDescription("Get Order Summary items count")
	public int getOrderSummeryItemCount() {
		logger.info("Get Order Summary items count");
		ExtentReportTest.getTest().info("Get Order Summary items count");
		try {
			String text = getText(orderSummeryItems);
			int number = Integer.parseInt(text.replaceAll("[^0-9]", ""));
			logger.info("Number of summery items: " + number);
			return number;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting order summery count");
		}
		return 0;
	}

	@MethodDescription("Remove all items from cart")
	public void removeAllItemsFromShoppingCart() {
		logger.info("Remove all items from cart");
		ExtentReportTest.getTest().info("GRemove all items from cart");
		try {
			while (true) {
				if (cartArea.isEmpty()) {
					logger.info("No items in cart.");
					break;
				}
				WebElement product = cartArea.get(0);
				click(product.findElement(By.cssSelector(".product-item-remove a")));
			}
		} catch (NoSuchElementException | NumberFormatException e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while removing all items from cart");
		} catch (StaleElementReferenceException e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while removing all items from cart");
		}
	}

	@MethodDescription("Get empty cart text")
	public String getEmptycartText() {
		logger.info("Get empty cart text");
		ExtentReportTest.getTest().info("Get empty cart text");
		try {
			longWait.until(ExpectedConditions.elementToBeClickable(emptyCartText));
			String textString = getText(emptyCartText);
			return textString;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting empty cart text");
		}
		return null;
	}

	@MethodDescription("Go to home page")
	public void clickOnHereLink() {
		logger.info("Go to home page");
		ExtentReportTest.getTest().info("Go to home page");
		try {
			longWait.until(ExpectedConditions.elementToBeClickable(hereLink));
			click(hereLink);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while going to home page");
		}
	}

	@MethodDescription("Check that cart page header is visible")
	public boolean isShoppingCartHeaderDisplayed() {
		logger.info("Check that cart page header is visible");
		ExtentReportTest.getTest().info("Check that cart page header is visible");
		return isDisplayed(header);
	}

	@MethodDescription("Check that changing product Qty will change total price")
	public boolean updateItemQtyFromShoppingCart(String number) {
		ExtentReportTest.getTest().info("Check that changing product Qty will change total price");
		logger.info("Check that changing product Qty will change total price");
		longWait.until(ExpectedConditions.visibilityOf(subTotalAmount));

		try {
			double toatalValueAfrerAdd = 0;
			// get pricing from item
			String price = cartArea.get(0).findElement(By.cssSelector(".col.price>span >span [class='price']"))
					.getText();
			cartArea.get(0).findElement(By.cssSelector("[title='Qty']")).clear();
			cartArea.get(0).findElement(By.cssSelector("[title='Qty']")).sendKeys(number);
			clickOnUpdatecartButton();
			String subTotal = cartArea.get(0).findElement(By.cssSelector(".col.subtotal>span>span [class='price']"))
					.getText();
			String subTotalCheckOut = cartPageSummery.get(0).findElement(By.cssSelector(".totals.sub [class='price']"))
					.getText();

			double priceNumber = Utils.parseCurrencyStringToDouble(price);
			double priceSubTotal = Utils.parseCurrencyStringToDouble(subTotal);
			double priceSubTotalCheckout = Utils.parseCurrencyStringToDouble(subTotalCheckOut);
			double multiplyPreceResult = Utils.multiplyPrice(priceNumber, number);

			if (multiplyPreceResult == priceSubTotalCheckout && priceSubTotal == priceSubTotalCheckout) {
				return true;
			}
			return false;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while calculating total price:");
		}
		return false;
	}

	@MethodDescription("Apply gift card coupon")
	public void applyGiftCard(String cardNumber) {
		try {
			longWait.until(ExpectedConditions.visibilityOf(giftCardApplyButton));
			fillText(applyGiftCardField, cardNumber);
			click(giftCardApplyButton);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"n exception occurred while applying gift coupons");
		}
	}

	@MethodDescription("Get toast message")
	public String getPageToastMessage() {
		return getToastMessage(generalErrorMessage);
	}

	@MethodDescription("Apply discount code")
	public void applyDiscointCode(String code) {
		longWait.until(ExpectedConditions.visibilityOf(applyDiscountCodeButton));
		fillText(applyDiscountCodeField, code);
		click(applyDiscountCodeButton);
	}

	@MethodDescription("Click on product name link")
	public void clickOnProductName() {
		logger.info("Get product link name");
		ExtentReportTest.getTest().info("Get product link name");
		try {
			click(cartArea.get(0).findElement(By.cssSelector(".product-item-name a")));
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting product link name");
			throw e;
		}
	}

	@MethodDescription("Click on product sku link")
	public void clickOnProductSku() {
		logger.info("Get product SKU link");
		ExtentReportTest.getTest().info("Get product SKU link");
		try {
			click(cartArea.get(0).findElement(By.cssSelector("div.product.sku a")));
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting product SKU link");
			throw e;
		}
	}

	@MethodDescription("Get product name")
	public String getProductName() {
		logger.info("Get product name");
		ExtentReportTest.getTest().info("Get product name");
		try {
			return getText(cartArea.get(0).findElement(By.cssSelector(".product-item-name a")));
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting product name");
			throw e;
		}
	}

	@MethodDescription("Get product sku")
	public String getProductSku() {
		logger.info("Get product sku");
		ExtentReportTest.getTest().info("Get product sku");
		try {
			return getText(cartArea.get(0).findElement(By.cssSelector("div.product.sku a")));
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting product SKU");
			throw e;
		}
	}
}
