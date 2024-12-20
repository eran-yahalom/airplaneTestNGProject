package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.LoggerFactory;
import annotations.MethodDescription;
import pages.BasePage;
import utils.ExtentReportTest;

/**************************
 * handles buttom components like: twitter,facebook,tiktok buttons
 **************************/
public class BottomComponentPage extends BasePage {

	@FindBy(css = "a[title='Facebook']")
	private WebElement facebookButton;

	@FindBy(css = "a[title='Twitter']")
	private WebElement twitterButton;

	@FindBy(css = "a[title='YouTube']")
	private WebElement youTubeButton;

	@FindBy(css = "a[title='Instagram']")
	private WebElement instagramButton;

	@FindBy(css = "a[title='TikTok']")
	private WebElement tikTokButton;

	// social network pages elements

	@FindBy(css = "#modal-header span span")
	private WebElement signInToX;

	@FindBy(css = "[aria-label='Create new account']")
	private WebElement createNewAccountButton;

	@FindBy(css = ".dynamic-text-view-model-wiz__h1")
	private WebElement youTubeText;

	@FindBy(css = "[type='submit']")
	private WebElement instaGramButton;

	@FindBy(css = "#header-login-button >div")
	private WebElement tikTokHeader;

	public BottomComponentPage(WebDriver driver) {
		super(driver);
	}

	@MethodDescription("Click on twitter icon")
	public void openTwitter() {
		ExtentReportTest.getTest().info("Click on twitter icon");
		logger.info("Click on twitter icon");
		click(twitterButton);
		moveToNewWindow();
	}

	@MethodDescription("Click on facebook icon")
	public void openFaceBook() {
		ExtentReportTest.getTest().info("Click on facebook icon");
		logger.info("Opening facebook page");
		click(facebookButton);
		moveToNewWindow();
	}

	@MethodDescription("Click on you tube icon")
	public void openYouTube() {
		ExtentReportTest.getTest().info("Click on YouTube icon");
		logger.info("Opening YouTube page");
		click(youTubeButton);
		moveToNewWindow();
	}

	@MethodDescription("Click on instagram icon")
	public void openInstagram() {
		ExtentReportTest.getTest().info("Click on instaGram icon");
		logger.info("Opening instaGram page");
		click(instagramButton);
		moveToNewWindow();
	}

	@MethodDescription("Click on Tik Tok icon")
	public void openTikTok() {
		ExtentReportTest.getTest().info("Click on TikTok icon");
		logger.info("Opening TikTok page");
		click(tikTokButton);
		moveToNewWindow();
	}

	@MethodDescription("Check that Twitter page will open")
	public boolean isItTwitterPage() {
		try {
			logger.info("Opening Twitter page");
			ExtentReportTest.getTest().info("Opening Twitter page");
			socialNetworkWait.until(ExpectedConditions.visibilityOf(signInToX));

			String title = driver.getTitle();
			logger.info("Title is: " + title);
			ExtentReportTest.getTest().info("Page title is: " + title);

			boolean isTwitterPage = title.contains("X / X");
			if (isTwitterPage) {
				ExtentReportTest.getTest().pass("Twitter page successfully opened.");
			} else {
				ExtentReportTest.getTest().fail("Twitter page did not open as expected. Title: " + title);
			}
			return isTwitterPage;

		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while checking Twitter page");
			return false;
		}
	}

	@MethodDescription("Check that FaceBook page will open")
	public boolean isItFaceBookPage() {
		try {
			ExtentReportTest.getTest().info("Opening FaceBook page");
			logger.info("Opening FaceBook page");

			socialNetworkWait.until(ExpectedConditions.elementToBeClickable(createNewAccountButton));

			String title = driver.getTitle();
			logger.info("Title is: " + title);
			ExtentReportTest.getTest().info("Page title is: " + title);

			boolean isFaceBookPage = title.contains("Airplane Shop |");
			if (isFaceBookPage) {
				ExtentReportTest.getTest().pass("FaceBook page successfully opened.");
			} else {
				ExtentReportTest.getTest().fail("FaceBook page did not open as expected. Title: " + title);
			}
			return isFaceBookPage;

		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while checking FaceBook page");
			return false;
		}
	}

	@MethodDescription("Check that YouTube page will open")
	public boolean isItYouTubePage() {
		try {
			ExtentReportTest.getTest().info("Opening YouTube page");
			logger.info("Opening Twitter page");

			socialNetworkWait.until(ExpectedConditions.elementToBeClickable(youTubeText));

			String title = driver.getTitle();
			logger.info("Title is: " + title);
			ExtentReportTest.getTest().info("Page title is: " + title);

			boolean isYouTubePage = title.contains("YouTube");
			if (isYouTubePage) {
				ExtentReportTest.getTest().pass("YouTube page successfully opened.");
			} else {
				ExtentReportTest.getTest().fail("YouTube page did not open as expected. Title: " + title);
			}
			return isYouTubePage;

		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while checking YouTube page");
			return false;
		}
	}

	@MethodDescription("Check that Instagram page will open")
	public boolean isItInstagramPage() {
		try {
			logger.info("Opening Instagram page");
			ExtentReportTest.getTest().info("Opening Instagram page");
			socialNetworkWait.until(ExpectedConditions.visibilityOf(instaGramButton));

			String title = driver.getTitle();
			logger.info("Title is: " + title);
			ExtentReportTest.getTest().info("Page title is: " + title);
			boolean isInstagram = title.contains("instagram");
			if (isInstagram) {
				ExtentReportTest.getTest().pass("instaGram page successfully opened.");
			} else {
				ExtentReportTest.getTest().fail("instaGram page did not open as expected. Title: " + title);
			}
			return isInstagram;

		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while checking InstaGram page");
			return false;
		}
	}

	@MethodDescription("Check that TikTok page will open")
	public boolean isItTikTokPage() {
		try {
			ExtentReportTest.getTest().info("Opening TikTok page");
			logger.info("Opening TikTok page");
			socialNetworkWait.until(ExpectedConditions.visibilityOf(youTubeText));
			String title = driver.getTitle();
			logger.info("Title is: " + title);
			ExtentReportTest.getTest().info("Page title is: " + title);
			boolean isTikTokPage = title.contains("airplaneshop");
			if (isTikTokPage) {
				ExtentReportTest.getTest().pass("TikTok page successfully opened.");
			} else {
				ExtentReportTest.getTest().fail("TikTok page did not open as expected. Title: " + title);
			}
			return isTikTokPage;

		} catch (Exception e) {
			ExtentReportTest.printExceptionToHTML(e, ExtentReportTest.getCurrentClassWithLineNumber(),
					"An exception occurred while checking TikTok page");
			return false;
		}
	}

	public boolean isSocialNetworkURLFound(String storedURL) {
		String webURL = driver.getCurrentUrl();
		ExtentReportTest.getTest().info("Checking if the current URL matches the stored URL");
		org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.info("Current URL: {}", webURL);
		logger.info("Stored URL: {}", storedURL);

		if (webURL.equalsIgnoreCase(storedURL)) {
			ExtentReportTest.getTest().pass("URLs match: " + webURL);
			logger.info("URLs match.");
			return true;
		} else {
			ExtentReportTest.getTest().fail("URLs do not match. Expected: " + storedURL + ", Found: " + webURL);
			logger.warn("URLs do not match.");
			return false;
		}
	}
}
