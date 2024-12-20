package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import annotations.MethodDescription;
import utils.ExtentReportTest;

public class PaymentMethodPage extends BasePage {

	// payment methods radio buttons
	@FindBy(css = "[name='payment[method]']")
	private List<WebElement> paymentMethodsRadioButtons;

	// credit card
	@FindBy(css = "#credit-card-number")
	private WebElement creditCardNumber;

	// experation date
	@FindBy(css = "[class='expirationDate']")
	private WebElement expiryDate;

	// CVV date
	@FindBy(css = "#cvv")
	private WebElement cvv;

	// place order button
	@FindBy(css = ".actions-toolbar > div > button[title='Place Order']:not(#braintree_paypal_place_order)")
	private WebElement placeOrderButton;

	// shipping data
	@FindBy(css = "div.ship-to >div >button")
	private WebElement shipToEditButton;

	@FindBy(css = "div.ship-via >div >button")
	private WebElement shipViaEditButton;

	@FindBy(css = ".message-error.error.message")
	private WebElement generalErrorMessage;

	// place order button
	@FindBy(css = ".action.primary.checkout[title='Place Order']:not([data-role='review-save'])")
	private WebElement placeOrderButton1;

	public PaymentMethodPage(WebDriver driver) {
		super(driver);
	}

	public String fillInCreditcard(String number, String expiry, String cvvNum) {
		logger.info("Entering Payment Method page");
		ExtentReportTest.getTest().info("Entering Payment Method page");
		paymentMethodsRadioButtons.get(0).click();
		fillInCreditCardNumber(number);
		fillInCreditCardExpiry(expiry);
		fillInCreditCardCVV(cvvNum);
		clickOnPlaceOrderButton();
		return getPageToastMessage();
	}

	@MethodDescription("Get toast message")
	public String getPageToastMessage() {
		logger.info("Getting toast message");
		longWait.until(ExpectedConditions.visibilityOf(generalErrorMessage));
		return getToastMessage(generalErrorMessage);
	}

	@MethodDescription("Fill credit card number")
	public void fillInCreditCardNumber(String creditCardNum) {
		logger.info("Entering credit card number");
		WebElement formCardElement = driver.findElement(By.cssSelector("#braintree_cc_number >iframe"));
		driver.switchTo().frame(formCardElement);
		creditCardNumber.clear();
		creditCardNumber.sendKeys(creditCardNum);
		driver.switchTo().defaultContent();
	}

	@MethodDescription("Fill expiry number")
	public void fillInCreditCardExpiry(String expiry) {
		logger.info("Entering credit card expiry");
		WebElement formExpiryElement = driver.findElement(By.cssSelector("#braintree_expirationDate >iframe"));
		driver.switchTo().frame(formExpiryElement);
		expiryDate.clear();
		expiryDate.sendKeys(expiry);
		driver.switchTo().defaultContent();
	}

	@MethodDescription("Fill CVV number")
	public void fillInCreditCardCVV(String cvvNumber) {
		logger.info("Entering credit card CVV");

		WebElement formCvvElement = driver.findElement(By.cssSelector("#braintree_cc_cid >iframe"));
		driver.switchTo().frame(formCvvElement);
		cvv.clear();
		cvv.sendKeys(cvvNumber);
		driver.switchTo().defaultContent();
	}

	@MethodDescription("Click on Place order button")
	public void clickOnPlaceOrderButton() {
		logger.info("Click on place order once");
		driver.findElement(
				By.cssSelector(".action.primary.checkout[title='Place Order']:not([data-role='review-save'])")).click();
		waiting(3000);
		logger.info("Click on place order twice");
		driver.findElement(
				By.cssSelector(".action.primary.checkout[title='Place Order']:not([data-role='review-save'])")).click();
		waiting(60000);
	}
}
