package pages;

import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import annotations.MethodDescription;
import utils.ExtentReportTest;
import utils.Utils;

public class MyWishListPage extends BasePage {

	// toast
	@FindBy(css = ".page.messages")
	private WebElement messageToast;

	@FindBy(css = ".message.info.empty")
	private WebElement emptyPageToast;

	// page header
	@FindBy(css = ".page.messages a")
	private WebElement messageToastHereLink;

	@FindBy(css = ".page-title")
	private WebElement header;

	// item areas
	// Item(s)
	@FindBy(css = "span.toolbar-number")
	private WebElement numOfPageItems;

	// items list
	@FindBy(css = ".product-item-info")
	private List<WebElement> area;

	@FindBy(css = ".product-item-name a")
	private WebElement itemnameLink;

	@FindBy(css = "span[id^='product-price']")
	private WebElement itemPrice;

	@FindBy(css = "textarea[id^='product-item-comment']")
	private WebElement itemCommentField;

	@FindBy(css = "[data-role='tocart']")
	private WebElement addAllTocartButton;

	@FindBy(css = "[id^='qty']")
	private WebElement itemQty;

	@FindBy(css = ".product-item-inner div:nth-child(3) a.action.edit")
	private WebElement editItemLink;

	@FindBy(css = ".product-item-inner div:nth-child(3) a[data-role='remove']")
	private WebElement removeItemLink;

	@FindBy(css = "[title='Update Wish List']")
	private WebElement updateWishListButton;

	@FindBy(css = "[title='Share Wish List']")
	private WebElement shareWishListButton;

	@FindBy(css = "[title='Add All to Cart']")
	private WebElement addAllToCartButton;

	@FindBy(css = "a[class='action back']")
	private WebElement backButton;

	// empty wish list page
	@FindBy(css = ".message.info.empty")
	private WebElement noItemsInWishListText;

	// back button
	@FindBy(css = "a[class='action back']")
	private WebElement wishListBackButton;

	public MyWishListPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@MethodDescription("Get empty page toast message")
	public String getEmptyPageToast() {
		String toastText;
		logger.info("Getting empty page toast");
		ExtentReportTest.getTest().info("Getting empty page toast");
		toastText = getToastMessage(emptyPageToast);
		longWait.until(ExpectedConditions.visibilityOf(emptyPageToast));
		return toastText;
	}

	@MethodDescription("Get page toast message")
	public String getPageToast() {
		String toastText;
		logger.info("Getting page toast");
		ExtentReportTest.getTest().info("Getting page toast");
		longWait.until(ExpectedConditions.visibilityOf(messageToast));
		toastText = getToastMessage(messageToast);
		return toastText;
	}

	@MethodDescription("Click on 'here' link inside the toast message")
	public void clickOnHereLink() {
		logger.info("Click on Here link");
		ExtentReportTest.getTest().info("Click on 'here' link inside the toast message");
		wait.until(ExpectedConditions.visibilityOf(messageToastHereLink));
		click(messageToastHereLink);
	}

	@MethodDescription("Get number items count")
	public int getItemsHeaderCount() {
		String text;
		logger.info("Get number items count");
		ExtentReportTest.getTest().info("Get number items count");
		text = getText(numOfPageItems);
		int number = Integer.parseInt(text.replaceAll("[^0-9]", ""));
		return number;
	}

	@MethodDescription("Get nimber of with list items")
	public int getWishListItemsCount() {
		logger.info("Number of products in page: " + area.size());
		ExtentReportTest.getTest().info("Get nimber of with list items");
		return area.size();
	}

	@MethodDescription("Click on update wish list button")
	public void clickOnUpdateWishListButton() {
		logger.info("Click on update wish list button");
		ExtentReportTest.getTest().info("Click on update wish list button");
		click(updateWishListButton);
	}

	@MethodDescription("Click on share wish list button")
	public void clickOnShareWishListButton() {
		logger.info("Click on share wish list button");
		ExtentReportTest.getTest().info("Click on share wish list button");
		click(shareWishListButton);
	}

	@MethodDescription("Click on add all to cart button")
	public void clickOnAddAllToCartButton() {
		logger.info("Click on add all to cart button");
		ExtentReportTest.getTest().info("Click on add all to cart button");
		click(addAllToCartButton);
		longWait.until(ExpectedConditions.visibilityOf(noItemsInWishListText));

	}

	@MethodDescription("Click on back button")
	public void clickOnBackButton() {
		logger.info("Click on back button");
		ExtentReportTest.getTest().info("Click on back button");
		click(backButton);
	}

	@MethodDescription("Remove all products from wish list")
	public void removeAllItemFromWishList() {
		logger.info("Removing all items from wishList");
		ExtentReportTest.getTest().info("Removing all items from wishList");
		try {
			while (true) {
				if (area.isEmpty()) {
					logger.info("No items in wish list.");
					break;
				}

				WebElement product = area.get(0);
				logger.info(product.findElement(By.cssSelector(".product-item-name a")).getText());

				click(product
						.findElement(By.cssSelector(".product-item-inner div:nth-child(3) a[data-role='remove']")));
				waiting(3000);
			}
		} catch (NoSuchElementException | NumberFormatException e) {
			ExtentReportTest.printExceptionToHTML(e,ExtentReportTest.getCurrentClassWithLineNumber(),"An exception occurred while removing all items from wish list");
		} catch (StaleElementReferenceException e) {
			ExtentReportTest.printExceptionToHTML(e,ExtentReportTest.getCurrentClassWithLineNumber(),"An exception occurred while removing all items from wish list");
		}
	}

	@MethodDescription("Is back button displayed")
	public boolean isbackButtonDisplayed() {
		logger.info("Is back button displayed");
		ExtentReportTest.getTest().info("s back button displayed");
		return isDisplayed(backButton);
	}

	@MethodDescription("Remove items from wish list  by name ")
	public void removeItemByName(String partialName) {
		String nameString;
		logger.info("Remove items from wish list  by name");
		ExtentReportTest.getTest().info("Remove items from wish list  by name");
		for (WebElement item : area) {
			nameString = item.findElement(By.cssSelector(".product-item-name a")).getText();
			if (nameString.contains(partialName)) {
				item.findElement(By.cssSelector(".product-item-inner div:nth-child(3) a[data-role='remove']")).click();
				waiting(2000);
				break;
			}
		}

	}

	@MethodDescription("Click on wish list back button")
	public void clickOnWishListbackButton() {
		logger.info("Click on wish list back button");
		ExtentReportTest.getTest().info("Click on wish list back button");
		wait.until(ExpectedConditions.visibilityOf(wishListBackButton));
		click(wishListBackButton);
	}

	@MethodDescription("Sum all wish list items qty")
	public int getSumOfAllItemsQty() {
		logger.info("Summing all wish list items qty");
		ExtentReportTest.getTest().info("Summing all wish list items qty");
		wait.until(ExpectedConditions.visibilityOf(addAllToCartButton));
		int sum = 0;
		int item = 0;
		String qtyString = null;
		for (WebElement element : area) {
			qtyString = element.findElement(By.cssSelector("[id^='qty']")).getAttribute("value");
			item = Utils.convertStringToInt(qtyString);
			sum += item;

		}
		logger.info("Sum all wish list items qty is: " + item);
		return item;

	}

}
