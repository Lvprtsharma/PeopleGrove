package utilities;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import base.BaseTest;
import extentlisteners.ExtentListeners;

public class CommonUtil extends BaseTest {

	/*
	 * This common Utility is used to create a common factory of all methods being
	 * used in test scripts
	 */
	
	// Click Method to click on an element
	public static void click(String locatorKey) {

		if (locatorKey.endsWith("_XPATH")) {
			try {
				driver.findElement(By.xpath(OR.getProperty(locatorKey))).click();
			}catch (ElementClickInterceptedException e) {
				Wait<WebDriver> wait = new WebDriverWait(driver,
						Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey)))).click();
			}
			log.info("Clicking on an Element : " + locatorKey.split("_")[0]);
			ExtentListeners.test.info("Clicking on an Element : " + locatorKey.split("_")[0]);
		}
	}

	// Type method to type in a text area
	public static void type(String locatorKey, String value) throws InterruptedException {

		if (locatorKey.endsWith("_XPATH")) {
			try {
				driver.findElement(By.xpath(OR.getProperty(locatorKey))).sendKeys(value);
			} catch (ElementNotInteractableException e) {
				Wait<WebDriver> wait = new WebDriverWait(driver,
						Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey))))
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
			Wait<WebDriver> wait = new WebDriverWait(driver,
					Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
			
			text = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey))))
					.getText();
		}
		log.info("Text Read from an Element " + locatorKey.split("_")[0] + " and Captured the value as " + text);
		ExtentListeners.test
				.info("Typing in an Element " + locatorKey.split("_")[0] + " and entered the value as " + text);
		return text;
	}

	// Click Method to click using action methods of action library
	public static void actionclick(String locatorKey) throws InterruptedException {
		if (locatorKey.endsWith("_XPATH")) {
			Thread.sleep(500);
			WebElement element = driver.findElement(By.xpath(OR.getProperty(locatorKey)));
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().build().perform();
		}
		log.info("Clicking on an Element : " + locatorKey.split("_")[0]);
		ExtentListeners.test.info("Clicking on an Element : " + locatorKey.split("_")[0]);

	}

	// Javascript click method to click for elements which are not clickable
	// otherwise
	public static void jsclick(String locatorKey) throws InterruptedException {
		if (locatorKey.endsWith("_XPATH")) {
			Thread.sleep(500);
			WebElement element = driver.findElement(By.xpath(OR.getProperty(locatorKey)));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
		}
		log.info("Clicking on an Element : " + locatorKey.split("_")[0]);
		ExtentListeners.test.info("Clicking on an Element : " + locatorKey.split("_")[0]);
	}

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
	public static boolean elementDisplayed(String locatorKey) throws InterruptedException {

		boolean result = false;
		try {
			Thread.sleep(1000);
			result = driver.findElement(By.xpath(OR.getProperty(locatorKey))).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println(locatorKey.split("_")[0] + " is not found!!");
		}
		log.info("Finding Element : " + locatorKey.split("_")[0]);
		ExtentListeners.test.info("Looking for an Element : " + locatorKey.split("_")[0]);
		return result;
	}

	// Method to check if element is already selected or not
	public static boolean elementSelected(String locatorKey) {

		boolean result = false;
		try {
			result = driver.findElement(By.xpath(OR.getProperty(locatorKey))).isSelected();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Checking if Element is already Selected : " + locatorKey.split("_")[0]);
		ExtentListeners.test.info("Checking if Element is already Selected  : " + locatorKey.split("_")[0]);
		return result;
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
					WebElement element = driver.findElement(By.xpath(OR.getProperty(locatorKey)));
					js.executeScript("arguments[0].scrollIntoView();", element);
					Thread.sleep(500);
				}
			} catch (NoSuchElementException e) {
				locatorKey = locatorKey.split("_")[0];
				System.out.println(locatorKey + " is not availble!!");
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
				Thread.sleep(500);
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
	public static void mouseHover(String locatorKey) throws InterruptedException {
		try {
			Thread.sleep(2000);
			WebElement element = driver.findElement(By.xpath(OR.getProperty(locatorKey)));
	        Actions actions = new Actions(driver);
	        actions.moveToElement(element).perform();
		}catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	// Get window handles and switch between them
	public void switchWindow() {
		try {
			Set<String> windowHandles = driver.getWindowHandles();
			// Switch to the pop-up window
			for (String windowHandle : windowHandles) {
				if (!windowHandle.equals(driver.getWindowHandle())) {
					driver.switchTo().window(windowHandle);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error in Switching between windows!!!");
		}
	}
}