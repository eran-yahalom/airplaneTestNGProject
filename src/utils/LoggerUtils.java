package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import bsh.This;
import components.HeaderComponentPage;
import pages.BasePage;

public class LoggerUtils {

	 public static void logInfo(String message) {
	        String className = getCallingClassName();
	        ExtentReportTest.getTest().info(className + ": " + message);
	        org.slf4j.Logger logger = LoggerFactory.getLogger(className);
	        logger.info(message);
	    }

	    public static void logWarning(String message) {
	        org.slf4j.Logger logger = LoggerFactory.getLogger(getCallingClassName());
	        ExtentReportTest.getTest().warning(message);
	        logger.warn(message);
	    }

	    public static void logError(String message, Exception e) {
	        org.slf4j.Logger logger = LoggerFactory.getLogger(getCallingClassName());
	        ExtentReportTest.getTest().fail(message);
	        logger.error(message, e);
	    }

	    public static String getCallingClassName() {
	        // Gets the stack trace to find the calling class
	        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	        for (StackTraceElement element : stackTrace) {
	            if (!element.getClassName().equals(LoggerUtils.class.getName()) 
	                && !element.getClassName().contains("java.lang.Thread")) {
	                return element.getClassName();
	            }
	        }
	        return LoggerUtils.class.getName(); // Fallback if no class found
	    }

}
