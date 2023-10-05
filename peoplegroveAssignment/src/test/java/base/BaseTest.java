package base;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

//Import statements, if any

public class BaseTest {

	// Declare a static WebDriver instance
	public static WebDriver driver;

	// Declare Properties objects for configuration and object repository
	protected static Properties OR = new Properties();
	protected static Properties config = new Properties();
	private static FileInputStream fis;

	// Initialize ExcelReader, WebDriver wait, and Logger
	public static WebDriver wait;
	public static Logger log = Logger.getLogger(BaseTest.class);

	// Setup method executed before the test suite
	@BeforeSuite
	public void setup() throws InterruptedException {
		// Configure log4j using the properties file
		PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");

		try {
			fis = new FileInputStream("./src/test/resources/properties/OR.properties");

		} catch (Exception e) {
			e.getStackTrace();
		}

		try {
			// Load object repository properties file
			OR.load(fis);
			log.info("OR Properties file loaded!!!");
		} catch (Exception e) {
			e.getStackTrace();
		}

		try {
			fis = new FileInputStream("./src/test/resources/properties/config.properties");

		} catch (Exception e) {
			e.getStackTrace();
		}

		try {
			// Load configuration properties file
			config.load(fis);
			log.info("Config Properties file loaded!!!");
		} catch (Exception e) {
			e.getStackTrace();
		}

		// Create a WebDriver instance based on the specified browser
		if (config.getProperty("browser").equals("chrome")) {
			
//			ChromeOptions options = new ChromeOptions();
//			options.addArguments("--headless=new");			
//			driver = new ChromeDriver(options);
			
			driver = new ChromeDriver();
			log.info("Chrome Browser Launched!!!");
		} else if (config.getProperty("browser").equals("firefox")) {
			driver = new FirefoxDriver();
			log.info("Firefox Browser Launched!!!");
		}

		// Navigate to the test URL
		driver.get(config.getProperty("testurl"));
		log.info("Navigated to : " + config.getProperty("testurl"));

		// Maximize the browser window
		driver.manage().window().maximize();

		// Set implicit wait timeout
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
	}

	// Teardown method executed after the test suite
	@AfterSuite
	public void tearDown() {
		// Quit the WebDriver instance
		driver.quit();
		log.info("Test Execution Completed!!");
	}
}