package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import annotations.MethodDescription;
import io.netty.handler.timeout.TimeoutException;
import utils.ExtentReportTest;
import utils.LoggerUtils;
import utils.Utils;

public class ShippingAddressPage extends BasePage {

	// page title
	@FindBy(css = "#shipping [class=\"step-title\"]")
	private WebElement header;

	// shipping address
	@FindBy(css = "#customerEmail")
	private WebElement customerEmail;

	// first name
	@FindBy(css = "[name='firstname']")
	private WebElement firstName;

	// last name
	@FindBy(css = "[name='lastname']")
	private WebElement lastName;

	// company
	@FindBy(css = "[name='company']")
	private WebElement company;

	// country id
	@FindBy(css = "[name='country_id']")
	private WebElement country;

	// region
	@FindBy(css = "[name='region']")
	private WebElement region;

	// city
	@FindBy(css = "[name='city']")
	private WebElement city;

	// zip code
	@FindBy(css = "[name='postcode']")
	private WebElement zipCode;

	@FindBy(css = "[name='telephone']")
	private WebElement phone;

	// street address
	@FindBy(css = "[name='street[0]']")
	private WebElement streetAddress;

	// Shipping Methods
	@FindBy(css = ".table-checkout-shipping-method")
	private WebElement shippingMethod;

	@FindBy(css = "[class='radio']")
	private List<WebElement> radioButtons;

	// loyalty card
	@FindBy(css = "#clutch_loyalty_number")
	private WebElement loyaltyCardNumber;

	// buttom page continue buttton
	@FindBy(css = ".button.action.continue.primary")
	private WebElement continueButton;

	// Order Summary
	// sub total
	@FindBy(css = ".totals.sub >td >span")
	private WebElement cartSubTotal;

	// shipping(economy)
	@FindBy(css = ".totals.shipping.excl >td >span")
	private WebElement shipping;

	// duties and taxes
	@FindBy(css = ".totals-tax >td >span")
	private WebElement dutiesandTaxes;

	// order total
	@FindBy(css = ".grand.totals >td >strong >span")
	private WebElement orderTotal;

	// pop up
	@FindAll({ @FindBy(css = ".z-close"), @FindBy(css = ".modal-footer >button> span"),
			@FindBy(css = ".modal-dismiss") })
	private List<WebElement> popupCloseButtons;

	// address validation pop up
	@FindBy(css = "#origin_address[value='origin']")
	private WebElement addressRadioButton;

	// pop up continue button
	@FindBy(css = ".modal-footer >button >span")
	private WebElement popUpContinueButton;

	@FindBy(css = ".modal-footer > button")
	private List<WebElement> buttons;

	// new address button
	@FindBy(css = ".action.action-show-popup")
	private WebElement neAddressButton;

	// loader
	@FindBy(css = ".loading-mask >div >img")
	private WebElement loaderGone;

	public ShippingAddressPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@MethodDescription("Select country")
	public void selectCountry(String selectString) {
		longWait.until(ExpectedConditions.elementToBeClickable(country));
		Select select = new Select(country);
		select.selectByValue(selectString);

	}

	@MethodDescription("Select state")
	public void selectState(String selectString) {
		longWait.until(ExpectedConditions.elementToBeClickable(region));
		Select select = new Select(region);
		select.selectByValue(selectString);

	}

	@MethodDescription("Close page pop up")
	public void closeSystemPopupIfPresent() {
		Utils.closeSystemPopup(driver, popupCloseButtons);
	}

	@MethodDescription("Fill in address(when address is not filled in my account)")
	public void addAShippingAddress(String countryName, String stateName) {
		Utils.waitForLoaderToDisappear(driver, By.cssSelector("div.loading-mask"), 100);
		LoggerUtils.logInfo("waiting");
		click(continueButton);
		approveAddressValidationPopUp();
		click(continueButton);
		Utils.waitForLoaderToDisappear(driver, By.cssSelector("div.loading-mask"), 100);
	}

	@MethodDescription("close address validation pop up")
	public void approveAddressValidationPopUp() {
		LoggerUtils.logInfo("close address validation pop up");
		try {
			waiting(4000);
			driver.findElement(By.cssSelector("#origin_address[value='origin']")).click();
			waiting(4000);
			driver.findElements(By.cssSelector(".modal-footer > button")).get(2).click();
		} catch (TimeoutException e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while searching Address validation popup not found");
		}
	}

}
