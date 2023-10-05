package utilities;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseTest;
import extentlisteners.ExtentListeners;

public class CommonUtil extends BaseTest {

	/*
	 * This common Utility is used to create a common factory of all methods being
	 * used in test scripts
	 */

	static Wait<WebDriver> wait = null; // Declare wait outside of try block
	static int maxRetries = 5; // Set the maximum number of retries
	static int retries = 0;

	// Explicit Wait Method
	public static Wait<WebDriver> explicitWait() {

		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wait;
	}

	// Click Method to click on an element
	public static void click(String locatorKey) {

		if (locatorKey.endsWith("_XPATH")) {

			while (retries < maxRetries) {
				try {
					driver.findElement(By.xpath(OR.getProperty(locatorKey))).click();
					break; // Break the loop if the actions are successful
				} catch (StaleElementReferenceException e) {
					System.out.println("Stale Element Reference Exception Occured!!");
				} catch (ElementClickInterceptedException e) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click();", driver.findElement(By.xpath(OR.getProperty(locatorKey))));
					break;
				} catch (NoSuchElementException e) {
					explicitWait()
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(locatorKey))))
							.click();
					break;
				}
				log.info("Clicking on an Element : " + locatorKey.split("_")[0]);
				ExtentListeners.test.info("Clicking on an Element : " + locatorKey.split("_")[0]);
				retries++;
			}
		}
	}

	// Type method to type in a text area
	public static void type(String locatorKey, String value) throws InterruptedException {

		if (locatorKey.endsWith("_XPATH")) {
			try {
				driver.findElement(By.xpath(OR.getProperty(locatorKey))).sendKeys(value);
			} catch (ElementNotInteractableException e) {
				explicitWait().until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey))))
						.sendKeys(value);
			} catch (NoSuchElementException e) {
				explicitWait().until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey))))
						.sendKeys(value);
			}
			log.info("Typing in an Element " + locatorKey.split("_")[0] + " and entered the value as " + value);
			ExtentListeners.test
					.info("Typing in an Element " + locatorKey.split("_")[0] + " and entered the value as " + value);
		}
	}

	// Get Text method to extract the text written on UI
	public static String getText(String locatorKey) {
		String text = "";
		try {
			text = driver.findElement(By.xpath(OR.getProperty(locatorKey))).getText();
		} catch (ElementNotInteractableException e) {
			text = explicitWait().until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey))))
					.getText();
		}
		log.info("Text Read from an Element " + locatorKey.split("_")[0] + " and Captured the value as " + text);
		ExtentListeners.test
				.info("Typing in an Element " + locatorKey.split("_")[0] + " and entered the value as " + text);
		return text;
	}

//	// Click Method to click using action methods of action library
//	public static void actionclick(String locatorKey) throws InterruptedException {
//		if (locatorKey.endsWith("_XPATH")) {
//			Thread.sleep(500);
//			WebElement element = driver.findElement(By.xpath(OR.getProperty(locatorKey)));
//			Actions actions = new Actions(driver);
//			actions.moveToElement(element).click().build().perform();
//		}
//		log.info("Clicking on an Element : " + locatorKey.split("_")[0]);
//		ExtentListeners.test.info("Clicking on an Element : " + locatorKey.split("_")[0]);
//
//	}

//	// Javascript click method to click for elements which are not clickable
//	// otherwise
//	public static void jsclick(String locatorKey) {
//		try {
//			if (locatorKey.endsWith("_XPATH")) {
//				Thread.sleep(500);
//				WebElement element = driver.findElement(By.xpath(OR.getProperty(locatorKey)));
//				JavascriptExecutor js = (JavascriptExecutor) driver;
//				js.executeScript("arguments[0].click();", element);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log.info("Clicking on an Element : " + locatorKey.split("_")[0]);
//		ExtentListeners.test.info("Clicking on an Element : " + locatorKey.split("_")[0]);
//	}

	// Enter method to perform enter action
	public static void enterClick(String locatorKey) throws InterruptedException {
		if (locatorKey.endsWith("_XPATH")) {
			Thread.sleep(500);
			driver.findElement(By.xpath(OR.getProperty(locatorKey))).sendKeys(Keys.ENTER);
		}
		log.info("Clicking Enter on an Element : " + locatorKey.split("_")[0]);
		ExtentListeners.test.info("Clicking Enter on an Element : " + locatorKey.split("_")[0]);
	}

	// Element Display method to check if an element is visible or not
	public static boolean isElementDisplayed(String locatorKey) throws InterruptedException {
		try {
			WebElement element = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(locatorKey))));

			log.info("Element '{" + locatorKey.split("_")[0] + "}' is displayed.");
			ExtentListeners.test.info("Looking for an Element: '{" + locatorKey.split("_")[0] + "}'");
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println(locatorKey.split("_")[0] + " is not found!!");
			return false;
		}
	}

	// Method to check if element is already selected or not
	public static boolean isElementSelected(String locatorKey) {
		try {
			WebElement element = driver.findElement(By.xpath(OR.getProperty(locatorKey)));
			boolean result = element.isSelected();

			log.info("Checking if Element '{" + locatorKey.split("_")[0] + "}' is already selected.");
			ExtentListeners.test.info("Checking if Element '{" + locatorKey.split("_")[0] + "}' is already selected.");

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Method to generate a random number
	public static int RandomNumberGenerator() {
		Random rand = new Random();
		int randomNumber = rand.nextInt(1000);
		return randomNumber;
	}

	// Scroll method to scroll either to a specific element or by specific
	// co-ordinates
	public static void scroll(String locatorKey, int horizontal, int vertical) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (locatorKey != null) {
			try {
				if (locatorKey.endsWith("_XPATH")) {

					WebElement element = wait
							.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey))));
					js.executeScript("arguments[0].scrollIntoView();", element);

				}
			} catch (NoSuchElementException e) {
				System.out.println(locatorKey.split("_")[0] + " is not availble!!");
			}
		} else {
			js.executeScript("window.scrollBy(" + horizontal + "," + vertical + ")");
		}
		log.info("Scrolling to the Element : " + locatorKey.split("_")[0]);
		ExtentListeners.test.info("Scrolling to the Element  : " + locatorKey.split("_")[0]);
	}

	// Method to get the size of all sub-elements of a particular element
	public static List<WebElement> elementSize(String locatorKey) {

		List<WebElement> elementList = null;
		if (locatorKey.endsWith("_XPATH")) {
			try {
				elementList = driver.findElements(By.xpath(OR.getProperty(locatorKey)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Checking the count of Element : " + locatorKey.split("_")[0]);
		ExtentListeners.test.info("Checking the count of Element  : " + locatorKey.split("_")[0]);
		return elementList;
	}

	// MouseHover Action
	public static void mouseHover(String locatorKey) {
		try {
			WebElement element = driver.findElement(By.xpath(OR.getProperty(locatorKey)));
			Actions actions = new Actions(driver);
			actions.moveToElement(element).perform();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	// Get window handles and switch between them
	public void switchWindow() {
		try {
	        for (String windowHandle : driver.getWindowHandles()) {
	            if (!windowHandle.equals(driver.getWindowHandle())) {
	                driver.switchTo().window(windowHandle);
	                break;
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error in switching between windows!!!");
	        e.printStackTrace();
	    }
	}
}