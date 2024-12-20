package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.*;
import java.lang.reflect.Method;

public class SocialMediaTest extends BaseTest {
	@BeforeMethod
	public void before(Method method) {
		String testName = method.getName();
		String stars = "*".repeat(testName.length() + 20);
		System.out.println(stars);
		System.out.println("**      Running test: " + testName + "      **");
		System.out.println(stars);
	}

	@Test(description = "Test that faceBook page will open")
	public void tc02_faceBookTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc02_faceBookTest");
		bottomComponentPage.openFaceBook();
		boolean isfaceBookPageDisplayed = bottomComponentPage.isItFaceBookPage();
		bottomComponentPage.backToMainWindow();
		try {
			Assert.assertTrue(isfaceBookPageDisplayed, "FaceBook page is not displayed");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Check that Instagram page will open")
	public void tc04_instagramTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc04_instagramTest");
		bottomComponentPage.openInstagram();
		boolean isInstegramPageDisplayed = bottomComponentPage.isItInstagramPage();
		bottomComponentPage.backToMainWindow();
		try {
			Assert.assertTrue(isInstegramPageDisplayed, "Instagram page is not displayed");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Check that TikTok page will open")
	public void tc05_tikTokTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc05_tikTokTest");
		bottomComponentPage.openTikTok();
		boolean isTikTokPageDisplayed = bottomComponentPage.isItTikTokPage();
		bottomComponentPage.backToMainWindow();
		try {
			Assert.assertTrue(isTikTokPageDisplayed, "tikTok page is not displayed");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Check that YouTube page will open")
	public void tc01_youTubeTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc01_youTubeTest");
		bottomComponentPage.openYouTube();
		boolean isYouTubePageDisplayed = bottomComponentPage.isItYouTubePage();
		bottomComponentPage.backToMainWindow();
		try {
			Assert.assertTrue(isYouTubePageDisplayed, "YouTube page is not displayed");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(description = "Check that twitter page will open")
	public void tc03_twitterTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc03_twitterTest");
		bottomComponentPage.openTwitter();
		boolean isTwitterPageLoaded = bottomComponentPage.isItTwitterPage();
		bottomComponentPage.backToMainWindow();
		try {
			Assert.assertTrue(isTwitterPageLoaded, "Twitter page is not displayed");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}
	}
}
