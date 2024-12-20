package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import annotations.MethodDescription;
import components.SortFilterAndPageItemsComponentPage;
import utils.ExtentReportTest;

public class ProductsPage extends BasePage {

	List<Double> priceList = new ArrayList<>();
	List<String> items = new ArrayList<>();
	SortFilterAndPageItemsComponentPage sortFilterAndPageItemsComponentPage;

	// buttom sort by item number
	@FindBy(css = "#limiter-bottom")
	private WebElement buttomShowFilter;

	// producrs areas
	@FindBy(css = "[class^='item product']:not([class*='odd last']):not([data-role='product-item'])")
	private List<WebElement> area;

	@FindBy(css = "[class^='product sku']")
	private List<WebElement> productItenNumber;

	@FindBy(css = "a.product-item-link")
	private List<WebElement> productItemNumber;

	@FindBy(css = "span[id^='product-price']")
	private List<WebElement> productItemPrice;

	@FindBy(css = "a.product-item-link")
	private WebElement productItemLinkName;

	@FindBy(css = "[title='Add to Cart']")
	private List<WebElement> addToCartButton;

	// ROW
	@FindBy(css = ".products.wrapper.list.products-list .list-wrapper tbody tr")
	private List<WebElement> rowArea;

	public ProductsPage(WebDriver driver) {
		super(driver);

	}

	@MethodDescription("Use buttom 'Show' filter to change number of items in page")
	public void filterByNumberOfItems(String selectString) {
		logger.info("Click on Show filter");
		try {
			Select select = new Select(buttomShowFilter);
			select.selectByValue(selectString);
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while checking show filter");
		}
	}

	@MethodDescription("Get number of items in page")
	public int getNumberOfItemsInPage() {
		logger.info("Get number of items in page");
		ExtentReportTest.getTest().info("Get number of items in page");
		int count = 0;
		try {
			waiting(5000);
			count = area.size();
			logger.info("Number of items in page: " + count);
		} catch (Exception e) {
			logger.error("Element not found");
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while checking number of items");
		}
		return count;
	}

	@MethodDescription("Add a random product to cart")
	public String addRandomProductToCart() {
		logger.info("Add a random product to cart");
		ExtentReportTest.getTest().info("Add a random product to cart");
		try {
			wait.until(ExpectedConditions.elementToBeClickable(area.get(0))); // 1 sec
			int item = area.size() - 1;
			Random random = new Random();
			int randomNumber = random.nextInt(item);
			String itemNumber = getText(productItenNumber.get(randomNumber));
			click(addToCartButton.get(randomNumber));
			waiting(5000);
			return itemNumber;
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"Failed to add a random product to cart");
		}
		return null;

	}

	@MethodDescription("Add product by name to cart")
	public void addProductByNameToCart(String productNameString) {
		String productHeader;
		logger.info("Add product by name to cart");
		ExtentReportTest.getTest().info("Add product by name to cart");
		try {
			for (WebElement product : area) {
				productHeader = product.findElement(By.cssSelector("a.product-item-link")).getText();
				if (productHeader.equals(productNameString)) {
					product.findElement(By.cssSelector("[title='Add to Cart']")).click();
					waiting(4000);
					logger.info("Successfully added product '{}' to cart", productNameString);
					return;
				}
			}
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while adding item to cart");
		}
	}

	@MethodDescription("Sort grid items by price")
	public boolean checkItemsAreSortedByPrice(String filterOption) {
		logger.info("Start Sort GRID items by price");
		ExtentReportTest.getTest().info("Start Sort GRID items by price");
		String productPrice;
		double amount;
		priceList = new ArrayList<>();
		boolean isSortCorrect = false;

		try {
			for (WebElement product : area) {
				productPrice = product.findElement(By.cssSelector("span[id^='product-price']")).getText();
				amount = Double.parseDouble(productPrice.replaceAll("[^\\d.]", ""));
				priceList.add(amount);
			}
			List<Double> sortedPriceList = new ArrayList<>(priceList);
			Collections.sort(sortedPriceList); // Sort from low to high (ascending order)
			isSortCorrect = priceList.equals(sortedPriceList);

			if (isSortCorrect) {
				logger.info("The grid items are correctly sorted by price in ascending order.");
			} else {
				logger.warn("The grid items are not sorted by price in ascending order.");
			}
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while sorting grid items by price");
		}
		return isSortCorrect;
	}

	@MethodDescription("Sort list items by price")
	public boolean checkListItemsAreSortedByPrice(String filterOption) {
		logger.info("Start Sort LIST items by price");
		ExtentReportTest.getTest().info("Start Sort LIST items by price");
		String productPrice;
		double amount;
		priceList = new ArrayList<>();
		boolean isSortCorrect = false;
		try {
			for (WebElement product : rowArea) {
				productPrice = product.findElement(By.cssSelector(".product-price")).getText();
				amount = Double.parseDouble(productPrice.replaceAll("[^\\d.]", ""));
				priceList.add(amount);

			}
			List<Double> sortedPriceList = new ArrayList<>(priceList);
			Collections.sort(sortedPriceList); // Sort from low to high (ascending order)
			isSortCorrect = priceList.equals(sortedPriceList);
			if (isSortCorrect) {
				logger.info("The list items are correctly sorted by price in ascending order.");
			} else {
				logger.warn("The list items are not sorted by price in ascending order.");
			}
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while sorting list by price");
		}
		return isSortCorrect;
	}

	@MethodDescription("Sort page items by sku")
	public boolean checkItemsAreSortedBySku(String filterOption) {
		logger.info("Start Sort GRID items by SKU");
		ExtentReportTest.getTest().info("Start Sort GRID items by SKU");
		String sku;
		items = new ArrayList<>();
		boolean isSortCorrect = false;
		try {
			for (WebElement product : area) {
				sku = product.findElement(By.cssSelector("[class^='product sku']")).getText();
				items.add(sku);
			}
			List<String> sortedSku = new ArrayList<>(items);
			Collections.sort(sortedSku); // Sort from low to high (ascending order)
			isSortCorrect = items.equals(sortedSku);
			if (isSortCorrect) {
				logger.info("The items are correctly sorted by SKU in ascending order.");
			} else {
				logger.warn("The items are not sorted by SKU in ascending order.");
			}
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while sorting list by SKU");
		}
		return isSortCorrect;
	}

	@MethodDescription("Click on product by his name  > open product page > return peoduct name")
	public String clickOnSelectedProductByName(String productname) {
		logger.info("Return product name");
		ExtentReportTest.getTest().info("Return product name");
		String productHeader;
		String pNameString = null;
		try {
			for (int i = 0; i < area.size(); i++) {
				productHeader = area.get(i).findElement(By.cssSelector("a.product-item-link")).getText();
				if (productHeader.contains(productname)) {
					pNameString = productHeader;
					area.get(i).findElement(By.cssSelector("a.product-item-link")).click();
					waiting(4000);
					break;
				}
			}
			if (pNameString == null) {
				logger.warn("Product '{}' not found in the product list", productname);
			}
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while selecting product by name");
		}
		return pNameString;

	}

	@MethodDescription("Does a search string matches product name in products list")
	public Boolean isProductnameInProductsList(String productname) {
		logger.info("Does a search string matches product name in products list");
		ExtentReportTest.getTest().info("Does a search string matches product name in products list");
		waiting(4000);
		String productHeader;
		try {
			for (int i = 0; i < area.size(); i++) {
				productHeader = area.get(i).findElement(By.cssSelector("a.product-item-link")).getText();
				if (!productHeader.contains(productname)) {
					return false;
				}
			}
		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while checking if product name is in item list");
		}
		return true;

	}
}
