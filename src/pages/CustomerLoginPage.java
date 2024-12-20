package pages;

import annotations.*;
import components.HeaderComponentPage;
import utils.ExtentReportTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CustomerLoginPage extends BasePage {
	private HeaderComponentPage headerComponent;
	@FindBy(css = "#email")
	private WebElement userEmail;

	@FindBy(css = "#pass")
	private WebElement userpassword;

	@FindBy(css = "#send2")
	private WebElement signInButton;

	@FindBy(css = "#pass-error")
	private WebElement passwordError;

	@FindBy(css = "#email-error")
	private WebElement emailError;

	@FindBy(css = ".message-error.error.message")
	private WebElement customerLoginError;

	public CustomerLoginPage(WebDriver driver) {
		super(driver);
		this.headerComponent = new HeaderComponentPage(driver);
//		  this.test = MyExtentReports.getTest();
	}

	@MethodDescription("Log in function")
	public String login(String user, String pass) {
		logger.info("Starting log in process");
		ExtentReportTest.getTest().info("Starting log in process");

		WebElement loginElement;
		int count = 7;
		while (count > 0) {
			loginElement = headerComponent.getLogInButton();
			if (loginElement.getText().equalsIgnoreCase("Log In")) {
				try {
					click(loginElement);
					logger.info("Log in button was clicked");
					break;
				} catch (Exception e) {
					ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
							"n exception occurred on logIn");
				}
			} else {
				waiting(2000);
				logger.info("We don't see the login button yet, retrying...");
			}
			count--;
		}
		try {
			longWait.until(ExpectedConditions.visibilityOf(userEmail));
			fillText(userEmail, user);
			fillText(userpassword, pass);

			logger.info("Filling in user credentials");

			logger.info("Clicking on the 'Sign In' button");
			click(signInButton);
			waiting(10000);

			logger.info("Checking login button text after clicking 'Sign In'");

			return getText(headerComponent.getLogInButton());

		} catch (Exception e) {
			// test.fail("Error during login process: " + e.getMessage());
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"n exception occurred on logIn");
			throw e; // Rethrow the exception after logging it
		}
	}

	@MethodDescription("Get login error according to the error place")
	public boolean isErrorMessageCorrect(String errorMsg, String fieldname) {
		logger.info("Starting log in process");
		ExtentReportTest.getTest().info("Starting log in process");

		fieldname = fieldname.toLowerCase();
		String errorTextString;
		String userErrorText;
		String passwordErrorText;

		if ("no password".equals(fieldname)) {
			errorTextString = getTextIfVisible(passwordError);
			return errorMsg.equals(errorTextString);
		} else if ("no user".equals(fieldname)) {
			errorTextString = getTextIfVisible(emailError);
			return errorMsg.equals(errorTextString);
		} else if ("no data".equals(fieldname)) {
			userErrorText = getTextIfVisible(emailError);
			passwordErrorText = getTextIfVisible(passwordError);
			return errorMsg.equals(userErrorText) && errorMsg.equals(passwordErrorText);
		} else if ("wrong creds".equals(fieldname)) {
			wait.until(ExpectedConditions.elementToBeClickable(customerLoginError)); // 1 sec
			errorTextString = getTextIfVisible(customerLoginError);
			return errorMsg.equals(errorTextString);
		} else {
			throw new IllegalArgumentException("Unexpected value: " + fieldname);
		}
	}

	@MethodDescription("Get toast message")
	public String getPageToastMessage() {
		logger.info("Get toast message");
		ExtentReportTest.getTest().info("Get toast message");
		try {
			String toast;
			socialNetworkWait.until(ExpectedConditions.visibilityOf(customerLoginError));
			toast = getToastMessage(customerLoginError);
			return toast;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting toast message");
		}
		return null;
	}

}
