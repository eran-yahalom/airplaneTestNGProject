package tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.*;

public class CustomerLoginTest extends BaseTest {
//	ExtentTest suite;
//	private ExtentReports extent;
//	private ExtentTest test;

	/* 7 test run success */
	@BeforeMethod
	public void before(Method method) {
		String testName = method.getName();
		String stars = "*".repeat(testName.length() + 20);
		System.out.println(stars);
		System.out.println("**      Running test: " + testName + "      **");
		System.out.println(stars);
	}

	@Test(description = "Check that create account button is removed after login")
	public void tc02_createAccountRemovedTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc02_createAccountRemovedTest");
		loginPage.login(Utils.readProperty("user"), Utils.readProperty("password"));
		boolean isCreateAccountButtonVisisble = headerComponent.isCreateAnAccountButtonRemoved();
		headerComponent.logOut();
		try {
			Assert.assertTrue(isCreateAccountButtonVisisble, "Create account button is not visible");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}

	@Test(dataProvider = "getData", description = "use incorect login information")
	public void tc03_loginWrongCredentialsrTest(String user, String password, String error, String position) {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc03_loginWrongCredentialsrTest");
		loginPage.login(user, password);
		Boolean isErrorMessageCorrect = loginPage.isErrorMessageCorrect(error, position);
		try {
			Assert.assertTrue(isErrorMessageCorrect, "Error message is not correct");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}

//
	@Test(description = "successful login test")
	public void tc04_logInTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc04_logInTest");
		String logInTextString = loginPage.login(Utils.readProperty("user"), Utils.readProperty("password"));
		headerComponent.logOut();
		try {
			Assert.assertEquals(logInTextString, Utils.readProperty("logOutText"), "Cant see log out text");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}

//
	@Test(description = "successful log out")
	public void tc01_logOutTest() {
		ExtentReportTest.createTest(this.getClass().getSimpleName() + "-tc01_logOutTest");
		boolean isLoggedOut = headerComponent.logOut();
		try {
			Assert.assertTrue(isLoggedOut, "Log out was not succesfull");
		} catch (AssertionError e) {
			ExtentReportTest.getTest().fail("Assertion failed: " + e.getMessage());
			throw e;
		}

	}

	@DataProvider
	public Object[][] getData() {
		Object[][] myData = { { "user1@yopmail.com", "xxxx11",
				"The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.",
				"wrong creds" }, { "user1@yopmail.com", "", "This is a required field.", "no password" },
				{ "", "123", "This is a required field.", "no user" },
				{ "", "", "This is a required field.", "no data" }, };
		return myData;
	}

//	
//	
//	
//	@Test(description = "Check that create account button is removed after login")
//	public void tc02_createAccountRemovedTest() {
//		try {
//			test.info("Logging in with valid user credentials");
//			loginPage.login(Utils.readProperty("user"), Utils.readProperty("password"));
//			test.info("Checking if 'Create Account' button is removed");
//			boolean isCreateAccountButtonVisible = headerComponent.isCreateAnAccountButtonRemoved();
//			headerComponent.logOut();
//			test.info("Logging out after the check");
//			Assert.assertTrue(isCreateAccountButtonVisible, "Create account button is not visible");
//			test.pass("Test passed: Create account button was correctly removed.");
//		} catch (Exception e) {
//			test.fail("Test failed: " + e.getMessage());
//			throw e; // Rethrow the exception to fail the test
//		}
//	}
//
//	@Test(dataProvider = "getData", description = "use incorrect login information")
//	public void tc03_loginWrongCredentialsTest(String user, String password, String error, String position) {
//		try {
//			test.info("Attempting to log in with incorrect credentials");
//			loginPage.login(user, password);
//			test.info("Verifying error message is displayed correctly");
//			Boolean isErrorMessageCorrect = loginPage.isErrorMessageCorrect(error, position);
//			Assert.assertTrue(isErrorMessageCorrect, "Error message is not correct");
//			test.pass("Test passed: Error message displayed correctly.");
//		} catch (Exception e) {
//			test.fail("Test failed: " + e.getMessage());
//			throw e; // Rethrow the exception to fail the test
//		}
//	}
//
//	@Test(description = "successful login test")
//	public void tc04_logInTest() {
//		try {
//			test.info("Logging in with valid user credentials");
//			String logInTextString = loginPage.login(Utils.readProperty("user"), Utils.readProperty("password"));
//			headerComponent.logOut();
//			test.info("Logging out after successful login");
//			Assert.assertEquals(logInTextString, Utils.readProperty("logOutText"), "Can't see log out text");
//			test.pass("Test passed: Successful login and logout.");
//		} catch (Exception e) {
//			test.fail("Test failed: " + e.getMessage());
//			throw e; // Rethrow the exception to fail the test
//		}
//	}
//
//	@Test(description = "successful log out")
//	public void tc01_logOutTest() {
//		try {
//			test.info("Logging out from the application");
//			boolean isLoggedOut = headerComponent.logOut();
//			Assert.assertTrue(isLoggedOut, "Log out was not successful");
//			test.pass("Test passed: Log out was successful.");
//		} catch (Exception e) {
//			test.fail("Test failed: " + e.getMessage());
//			throw e; // Rethrow the exception to fail the test
//		}
//	}
//
//	@DataProvider
//	public Object[][] getData() {
//		return new Object[][] { { "user1@yopmail.com", "xxxx11",
//				"The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.",
//				"wrong creds" }, { "user1@yopmail.com", "", "This is a required field.", "no password" },
//				{ "", "123", "This is a required field.", "no user" },
//				{ "", "", "This is a required field.", "no data" } };
//	}
}
