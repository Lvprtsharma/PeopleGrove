package utilities;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
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

	static int maxRetries = 5; // Set the maximum number of retries
	static int retries = 0;

	// Explicit Wait Method
	public static Wait<WebDriver> explicitWait() {
		Wait<WebDriver> wait = getWait();
		try {
			wait = new WebDriverWait(getDriver(),
					Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wait;
	}

	// Click Method to click on an element
	public void click(String locatorKey) {

		if (locatorKey.endsWith("_XPATH")) {

			while (retries < maxRetries) {
				try {
					getDriver().findElement(By.xpath(OR.getProperty(locatorKey))).click();
					break; // Break the loop if the actions are successful
				} catch (StaleElementReferenceException e) {
					System.out.println("Stale Element Reference Exception Occured!!");
				} catch (ElementClickInterceptedException e) {
					JavascriptExecutor js = (JavascriptExecutor) getDriver();
					js.executeScript("arguments[0].click();",
							getDriver().findElement(By.xpath(OR.getProperty(locatorKey))));
					break;
				} catch (NoSuchElementException e) {
					explicitWait()
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(locatorKey))))
							.click();
					break;
				} catch (TimeoutException e) {
					explicitWait()
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(locatorKey))))
							.click();
					break;
				}
				getLog().info("Clicking on an Element : " + locatorKey.split("_")[0]);
				ExtentListeners.test.info("Clicking on an Element : " + locatorKey.split("_")[0]);
				retries++;
			}
		}
	}

	// Clear Method to clear on an element
	public void clear(String locatorKey) {

		if (locatorKey.endsWith("_XPATH")) {

			while (retries < maxRetries) {
				try {
					getDriver().findElement(By.xpath(OR.getProperty(locatorKey))).clear();
					break; // Break the loop if the actions are successful
				} catch (StaleElementReferenceException e) {
					System.out.println("Stale Element Reference Exception Occured!!");
				} catch (ElementClickInterceptedException e) {
					JavascriptExecutor js = (JavascriptExecutor) getDriver();
					js.executeScript("arguments[0].clear();",
							getDriver().findElement(By.xpath(OR.getProperty(locatorKey))));
					break;
				} catch (NoSuchElementException e) {
					explicitWait()
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(locatorKey))))
							.clear();
					break;
				}
				getLog().info("Clearing an Element : " + locatorKey.split("_")[0]);
				ExtentListeners.test.info("Clearing an Element : " + locatorKey.split("_")[0]);
				retries++;
			}
		}
	}

	// Type method to type in a text area
	public void type(String locatorKey, String value) {

		if (locatorKey.endsWith("_XPATH")) {
			try {
				getDriver().findElement(By.xpath(OR.getProperty(locatorKey))).sendKeys(value);
			} catch (ElementNotInteractableException e) {
				explicitWait().until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey))))
						.sendKeys(value);
			} catch (NoSuchElementException e) {
				explicitWait().until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey))))
						.sendKeys(value);
			}
			getLog().info("Typing in an Element " + locatorKey.split("_")[0] + " and entered the value as " + value);
			ExtentListeners.test
					.info("Typing in an Element " + locatorKey.split("_")[0] + " and entered the value as " + value);
		}
	}

	// Get Text method to extract the text written on UI
	public String getText(String locatorKey) {
		String text = "";
		try {
			text = getDriver().findElement(By.xpath(OR.getProperty(locatorKey))).getText();
		} catch (ElementNotInteractableException e) {
			text = explicitWait().until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey))))
					.getText();
		}
		getLog().info("Text Read from an Element " + locatorKey.split("_")[0] + " and Captured the value as " + text);
		ExtentListeners.test
				.info("Typing in an Element " + locatorKey.split("_")[0] + " and entered the value as " + text);
		return text;
	}

//	// Click Method to click using action methods of action library
//	public static void actionclick(String locatorKey) throws InterruptedException {
//		if (locatorKey.endsWith("_XPATH")) {
//			Thread.sleep(500);
//			WebElement element = getDriver().findElement(By.xpath(OR.getProperty(locatorKey)));
//			Actions actions = new Actions(getDriver());
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
//				WebElement element = getDriver().findElement(By.xpath(OR.getProperty(locatorKey)));
//				JavascriptExecutor js = (JavascriptExecutor) getDriver();
//				js.executeScript("arguments[0].click();", element);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log.info("Clicking on an Element : " + locatorKey.split("_")[0]);
//		ExtentListeners.test.info("Clicking on an Element : " + locatorKey.split("_")[0]);
//	}

	// Enter method to perform enter action
	public void enterClick(String locatorKey) {
		if (locatorKey.endsWith("_XPATH")) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getDriver().findElement(By.xpath(OR.getProperty(locatorKey))).sendKeys(Keys.ENTER);
		}
		getLog().info("Clicking Enter on an Element : " + locatorKey.split("_")[0]);
		ExtentListeners.test.info("Clicking Enter on an Element : " + locatorKey.split("_")[0]);
	}

	// Element Display method to check if an element is visible or not
	public boolean isElementDisplayed(String locatorKey, int maxRetry) {

		while (retries < maxRetry) {
			try {
				WebElement element = explicitWait()
						.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(locatorKey))));

				getLog().info("Element '{" + locatorKey.split("_")[0] + "}' is displayed.");
				ExtentListeners.test.info("Looking for an Element: '{" + locatorKey.split("_")[0] + "}'");

				return element.isDisplayed();
			} catch (Throwable e) {
				System.out.println(locatorKey.split("_")[0] + " is not found!! Retry: " + (retries + 1));
				retries++;
			}
		}
		return false;

	}

	// Method to check if element is already selected or not
	public boolean isElementSelected(String locatorKey) {
		try {
			WebElement element = getDriver().findElement(By.xpath(OR.getProperty(locatorKey)));
			boolean result = element.isSelected();

			getLog().info("Checking if Element '{" + locatorKey.split("_")[0] + "}' is already selected.");
			ExtentListeners.test.info("Checking if Element '{" + locatorKey.split("_")[0] + "}' is already selected.");

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Method to generate a random number
	public int RandomNumberGenerator() {
		Random rand = new Random();
		int randomNumber = rand.nextInt(1000);
		return randomNumber;
	}

	// Scroll method to scroll either to a specific element or by specific
	// co-ordinates
	public void scroll(String locatorKey, int horizontal, int vertical) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		if (locatorKey != null) {
			try {
				if (locatorKey.endsWith("_XPATH")) {

					WebElement element = getDriver().findElement(By.xpath(OR.getProperty(locatorKey)));
					js.executeScript("arguments[0].scrollIntoView();", element);

				}
			} catch (Exception e) {
				System.out.println(locatorKey.split("_")[0] + " is not availble!!");
			}
		} else {
			js.executeScript("window.scrollBy(" + horizontal + "," + vertical + ")");
		}
		getLog().info("Scrolling to the Element : " + locatorKey.split("_")[0]);
		ExtentListeners.test.info("Scrolling to the Element  : " + locatorKey.split("_")[0]);
	}

	// Method to get the size of all sub-elements of a particular element
	public List<WebElement> checkElementSize(String locatorKey) {

		List<WebElement> elementList = null;
		if (locatorKey.endsWith("_XPATH")) {
			try {
				elementList = getDriver().findElements(By.xpath(OR.getProperty(locatorKey)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		getLog().info("Checking the count of Element : " + locatorKey.split("_")[0]);
		ExtentListeners.test.info("Checking the count of Element  : " + locatorKey.split("_")[0]);
		return elementList;
	}

	// MouseHover Action
	public void mouseHover(String locatorKey) {

		while (retries < maxRetries) {
			try {
				WebElement element = explicitWait()
						.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey))));
				Actions actions = new Actions(getDriver());
				actions.moveToElement(element).perform();
				break;
			} catch (NoSuchElementException e) {
				System.out.println("Looking for Element : " + locatorKey.split("_")[0]);
				retries++;
			}
		}
	}

	// Get window handles and switch between them
	public void switchWindow() {
		try {
			for (String windowHandle : getDriver().getWindowHandles()) {
				if (!windowHandle.equals(getDriver().getWindowHandle())) {
					getDriver().switchTo().window(windowHandle);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error in switching between windows!!!");
			e.printStackTrace();
		}
	}
}