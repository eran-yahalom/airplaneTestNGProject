package components;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import annotations.MethodDescription;
import pages.BasePage;
import utils.ExtentReportTest;

/**************************
 * handles top header components like: search box login/logout ,cart,search
 **************************/

public class HeaderComponentPage extends BasePage {

	List<String> items = new ArrayList<>();
	private WebDriver driver;

	@FindBy(css = "a[title='Airplane Shop']")
	protected WebElement airplaneShopHeader;

	// Create New Customer Account/Customer Login headers
	@FindBy(css = "h1.page-title")
	protected WebElement header;

//log in
	@FindBy(css = ".header.content .header.links [data-label='or'] a")
	private WebElement logInButton;

	// serarch
	@FindBy(css = "input#search[type='text']")
	protected WebElement searchField;

	// magnify glass
	@FindBy(css = ".field.search div.actions > button[type='submit']")
	protected WebElement magnifyGlass;

	// My account (only login user)
	@FindBy(css = ".custom-block >div>ul>li:nth-child(1) >a")
	protected WebElement myAccountButton;

	@FindBy(css = ".panel.header >ul>li:nth-child(3) >a")
	protected WebElement createAnAccountButton;

	// Logout
	@FindBy(css = ".panel.header .link.authorization-link")
	protected WebElement logOutButton;

	// cart
	@FindBy(css = "a.action.showcart")
	private WebElement shoppingcart;

	@FindBy(css = ".subtitle.empty")
	private WebElement emptyCartText;

	@FindBy(css = ".action.showcart .counter.qty")
	private List<WebElement> cartBadge;

	// items in cart pop up

	@FindBy(css = "[data-role='product-item']")
	private List<WebElement> cartProducts;

	@FindBy(css = "[data-role='product-item'] a")
	private WebElement productImageLink;

	@FindBy(css = "[id^='cart-item']")
	private WebElement productQty;

	// close cart
	@FindBy(css = "#btn-minicart-close")
	private WebElement closeCartButton;

	// remove item from cart pop up
	@FindBy(css = ".action-primary.action-accept")
	private WebElement removePopUpOK;

	@FindBy(css = ".action-secondary.action-dismiss")
	private WebElement removePopUpCancel;

	@FindBy(css = ".modal-popup.confirm._show [data-role='closeBtn']")
	private WebElement removePopUpClose;

	// product name in cart
	@FindBy(css = ".product-item-sku")
	private WebElement productName;

	// buttons view cart/checkout
	@FindBy(css = "a.action.viewcart")
	private WebElement viewAndEditCartButton;

	@FindBy(css = "#top-cart-btn-checkout")
	private WebElement goToCheckOutButton;

	// cart item number
	@FindBy(css = "[class='count']")
	private WebElement cartItemCount;

	@FindBy(css = ".message.notice div")
	private WebElement toastMessage;

	@FindBy(css = "")
	private WebElement logoutSuccess;

	public HeaderComponentPage(WebDriver driver) {
		super(driver);

	}

	@MethodDescription("Get login button WebElement")
	public WebElement getLogInButton() {
		logger.info("Get login button WebElement");
		return logInButton;
	}

	@MethodDescription("Get cart badge number")
	public int getCartBadgeCount() {
		logger.info("Get cart badge number");
		ExtentReportTest.getTest().info("Get cart badge number");
		try {
			if (!cartBadge.isEmpty()) {
				String badgeText = getText(cartBadge.get(0));
				logger.info("Badge number : " + Integer.parseInt(badgeText));
				return Integer.parseInt(badgeText);
			} else {
				logger.info("No badge was found so badge is 0");
				return 0;
			}

		} catch (NoSuchElementException | NumberFormatException e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while getting cart badge number");
			return 0;
		}
	}

	@MethodDescription("Rerurn true if cart empty text is visible,false if not visible")
	public boolean getEmptyCartText() {
		logger.info("Rerurn true if cart empty text is visible,false if not visible");
		ExtentReportTest.getTest().info("Rerurn true if cart empty text is visible,false if not visible");
		try {
			return getText(emptyCartText).equals("You have no items in your shopping cart.");
		} catch (NoSuchElementException | NumberFormatException e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while checking that cart is empty");
			return false;
		}
	}

	@MethodDescription("Click on the cart icon")
	public void openCart() {
		logger.info("Click on the cart icon");
		ExtentReportTest.getTest().info("Click on the cart icon");
		try {
			click(shoppingcart);
			waiting(2000);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while opening cart");
		}
	}

	@MethodDescription("Close cart")
	public void closeCart() {
		ExtentReportTest.getTest().info("Attempting to close the cart.");
		logger.info("Attempting to close the cart.");

		try {

			longWait.until(ExpectedConditions.elementToBeClickable(closeCartButton));

			// Click the close cart button
			click(closeCartButton);
			logger.info("Clicked on the close cart button.");
			ExtentReportTest.getTest().info("Clicked on the close cart button.");

			wait.until(ExpectedConditions.invisibilityOf(closeCartButton));
			logger.info("Close cart button is no longer visible.");
			ExtentReportTest.getTest().pass("Cart closed successfully.");
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"Failed to close the cart");
		}
	}

	@MethodDescription("Create New Customer Account/Customer Login headers")
	public String getPageHaeder() {
		logger.info("Get Create New Customer Account/Customer Login headers");
		return getText(header);
	}

	@MethodDescription("Log out from account")
	public boolean logOut() {
		int count = 7;
		ExtentReportTest.getTest().info("Starting log out process");
		logger.info("Starting log out process");

		while (count > 0) {
			if (logOutButton.getText().equalsIgnoreCase("Log Out")) {
				try {
					click(logOutButton);
					logger.info("Log out button was clicked");
					break;
				} catch (Exception e) {
					ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
							"Error clicking log out button");
				}
			} else {
				waiting(2000);
				logger.info("We don't see log out button yet, retrying...");
			}
			count--;
		}

		checkThatLogOutMessageIsRemoved();

		try {
			boolean isLoggedOut = getText(logOutButton).equals("LOG IN");
			if (isLoggedOut) {
			} else {
			}
			return isLoggedOut;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while logging out");
		}
		return false;
	}

	@MethodDescription("Check that logout success message is removedp")
	public void checkThatLogOutMessageIsRemoved() {
		boolean isRemoved = false;
		logger.info("Logout success message: " + getText(header));
		ExtentReportTest.getTest().info("Check that logout success message is removed");
		try {

			wait.until(ExpectedConditions.visibilityOf(header));
			wait.until(ExpectedConditions.invisibilityOf(header));
			isRemoved = true;
			logger.info("Logout success message has been removed.");

			logger.info("Logout success message has been removed.");
		} catch (TimeoutException | NoSuchElementException e) {
			logger.warn("Error during logout message removal: " + e.getMessage());
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"n exception occurred while checking that log out message is removed");
		}
		if (!isRemoved) {
			logger.warn("Logout success message was not removed successfully.");
		}
	}

	@MethodDescription("Remove product by name from cart pop up")
	public void removeProductFromChart(String productname) {
		logger.info("Remove product by name from cart pop up");
		ExtentReportTest.getTest().info("Remove product by name from cart pop up");
		try {
			for (WebElement product : cartProducts) {
				String productHeader = product.findElement(By.cssSelector(".product-item-sku")).getText();
				if (productHeader.equals(productname)) {
					product.findElement(By.cssSelector(".secondary.remove")).click();
					approveDeleteItemPopUp();
					break;
				}
			}
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while removing a product");
		}
	}

	@MethodDescription("Approve and close the delete pop up")
	public void approveDeleteItemPopUp() {
		try {
			logger.info("Approve and close the delete pop up");
			ExtentReportTest.getTest().info("Aprove and close the delete pop up");
			wait.until(ExpectedConditions.elementToBeClickable(removePopUpOK));
			click(removePopUpOK);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while approving closing the delete pop up");
		}
	}

	public WebElement getRemovePopUpOKElement() {
		logger.info("Get remove pop up OK element");
		ExtentReportTest.getTest().info("Get remove pop up OK element");
		try {
			return driver.findElement(By.cssSelector(".action-primary.action-accept"));
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"Remove pop up OK is not displayed");
			return null;
		}

	}

	@MethodDescription("Remove all products from cart pop up")
	public void removeAllItemFromCart() {
		logger.info("Remove all products from cart pop up");
		ExtentReportTest.getTest().info("Remove all products from cart pop up");
		try {
			while (true) {
				if (cartProducts.isEmpty()) {
					logger.info("No items in cart.");
					break;
				}

				WebElement product = cartProducts.get(0);
				logger.info(product.findElement(By.cssSelector(".product-item-sku")).getText());

				click(product.findElement(By.cssSelector(".secondary.remove")));
				wait.until(ExpectedConditions.elementToBeClickable(removePopUpOK));
				click(removePopUpOK);
				waiting(8000);
			}
		} catch (NoSuchElementException | NumberFormatException e) {
			logger.error("Can't find items on the page.");
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while removing all items from cart");
		} catch (StaleElementReferenceException e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"Test failed due to an exception");
		}
	}

	@MethodDescription("Search that a product with a specific SKU is in cart")
	public boolean isItemInCart(String itemSkuNumber) {
		logger.info("Search that a product with a specific SKU is in cart");
		ExtentReportTest.getTest().info("Search that a product with a specific SKU is in cart");
		try {
			for (WebElement product : cartProducts) {
				String productItemNumber = product.findElement(By.cssSelector(".product-item-sku")).getText();
				if (itemSkuNumber.equals(productItemNumber)) {
					return true;
				}
			}
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while checking productSKU in cart");
		}
		return false;
	}

	@MethodDescription("Search an item")
	public void search(String searchText) {
		logger.info("Search an item");
		ExtentReportTest.getTest().info("Search an item");
		try {
			fillText(searchField, searchText);
			click(magnifyGlass);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while searching");
		}
	}

	@MethodDescription("Click on view and edit cart button")
	public void clickOnViewAndEditCartButton() {
		logger.info("Click on view and edit cart button");
		ExtentReportTest.getTest().info("Click on view and edit cart button");
		try {
			click(viewAndEditCartButton);
			wait.until(ExpectedConditions.visibilityOf(header));
			waiting(8000);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while viewing and editing cart button");
		}
	}

	@MethodDescription("Click on My Account button")
	public void clickOnMyAccountButton() {
		logger.info("Click on My Account button");
		ExtentReportTest.getTest().info("Click on My Account button");
		try {
			waiting(8000);
			click(myAccountButton);
			wait.until(ExpectedConditions.visibilityOf(header));
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"Click on My Account button");
		}
	}

	@MethodDescription("Click on go to checkout button")
	public void clickOnGoToCheckOutButton() {
		logger.info("Click on go to checkout button");
		ExtentReportTest.getTest().info("Click on go to checkout button");
		try {
			click(goToCheckOutButton);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"Click on Go to checkout");
		}
	}

	@MethodDescription("Get top items count")
	public int getTopItemCount() {
		logger.info("Getting top items count from cart header");
		ExtentReportTest.getTest().info("Getting top items count from cart header");
		try {
			wait.until(ExpectedConditions.visibilityOf(cartItemCount));
			String text = getText(cartItemCount);
			int itemCount = Integer.parseInt(text.replaceAll("[^0-9]", ""));
			logger.info("Number of items in cart header: " + itemCount);
			return itemCount;
		} catch (Exception e) {
			logger.warn("Cart item count not available or not interactable. Defaulting to 0.");
			return 0;
		}
	}

	@MethodDescription("Click on product icon in order to open product page")
	public void clickOnProductIcon() {
		try {
			logger.info("Get toast message");
			ExtentReportTest.getTest().info("Get toast message");
			click(productImageLink);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"Cant get toast message");
		}
	}

	@MethodDescription("Click on product icon in order to open product page")
	public String getProductQuantity() {
		logger.info("Click on product icon in order to open product page");
		ExtentReportTest.getTest().info("Click on product icon in order to open product page");
		try {
			wait.until(ExpectedConditions.visibilityOf(productQty));
			return productQty.getAttribute("value");
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"Cant open product page");
			return null;
		}
	}

	@MethodDescription("Check that create account button is not displayed")
	public boolean isCreateAnAccountButtonRemoved() {
		ExtentReportTest.getTest().info("Check that create account button is not displayed");
		longWait.until(ExpectedConditions.visibilityOf(myAccountButton));
		try {
			return !getText(createAnAccountButton).equalsIgnoreCase("Create an Account");
		} catch (NoSuchElementException e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred when searching for create account button");
			return false;
		}
	}

	@MethodDescription("Get toast message")
	public String getPageToastMessage() {
		logger.info("Get toast message");
		ExtentReportTest.getTest().info("Get toast message");
		try {
		socialNetworkWait.until(ExpectedConditions.visibilityOf(toastMessage));
		return getToastMessage(toastMessage);
		}catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"Cant get toast message");
			return null;
		}
	}

	@MethodDescription("Open cart, remove all items ,close cart")
	public void cleanCart() {
		ExtentReportTest.getTest().info("Straring to remove items from cart");
		logger.info("Straring to remove items from cart");
		openCart();
		removeAllItemFromCart();
		closeCart();
		logger.info("Cart is empty");
	}
}
