package base;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {

	private static WebDriver driver;

	protected static Properties OR = new Properties();
	protected static Properties config = new Properties();
	private static FileInputStream fis;

	private static Wait<WebDriver> wait;
	private static Logger log = Logger.getLogger(BaseTest.class);

	@BeforeSuite
	public void setup() {
		PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
		try {
			fis = new FileInputStream("./src/test/resources/properties/OR.properties");

		} catch (Exception e) {
			e.getStackTrace();
		}

		try {
			OR.load(fis);
			getLog().info("OR Properties file loaded!!!");
		} catch (Exception e) {
			e.getStackTrace();
		}

		try {
			fis = new FileInputStream("./src/test/resources/properties/config.properties");

		} catch (Exception e) {
			e.getStackTrace();
		}

		try {
			config.load(fis);
			getLog().info("Config Properties file loaded!!!");
		} catch (Exception e) {
			e.getStackTrace();
		}

		switch (config.getProperty("browser")) {
		case "chrome":

//			ChromeOptions options = new ChromeOptions();
//			options.addArguments("--headless=new");			
//			driver = new ChromeDriver(options);
//			System.out.println("Testing in Headless Browser!!");

			driver = new ChromeDriver();
			getLog().info("Chrome Browser Launched!!!");
			break;

		case "firefox":
			driver = new FirefoxDriver();
			getLog().info("Firefox Browser Lauched!!!");
			break;

		default:
			System.out.println("browser : " + config.getProperty("browser")
					+ " is invalid. Launching Chrome as browser of choice..");
			driver = new ChromeDriver();
			getLog().info("Chrome Browser Launched!!!");
		}

		driver.get(config.getProperty("testurl"));
		getLog().info("Navigated to : " + config.getProperty("testurl"));

		driver.manage().window().maximize();
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static Wait<WebDriver> getWait() {
		return wait;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		BaseTest.log = log;
	}

	public abstract void click(String locatorKey);

	public abstract void clear(String locaterKey);

	public abstract void type(String locatorKey, String value);

	public abstract String getText(String locatorKey);

	public abstract void enterClick(String locatorKey);

	public abstract boolean isElementDisplayed(String locatorKey, int maxRetry);

	public abstract boolean isElementSelected(String locatorKey);

	public abstract int RandomNumberGenerator();

	public abstract void scroll(String locatorKey, int horizontal, int vertical);

	public abstract List<WebElement> checkElementSize(String locatorKey);

	public abstract void mouseHover(String locatorKey);

	public abstract void switchWindow();

	@AfterSuite
	public void tearDown() {
		driver.quit();
		getLog().info("Test Execution Completed!!");
	}
}