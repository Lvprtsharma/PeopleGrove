package extentlisteners;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import base.BaseTest;

//import base.BaseTest;

public class ExtentManager {

	private static ExtentReports extent;
	public static String fileName;

	// Method to create an instance of ExtentReports and configure the HTML reporter
	public static ExtentReports createInstance(String fileName) {
		// Create an ExtentSparkReporter with the specified file name
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

		// Configure the HTML reporter's settings
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);

		// Create an instance of ExtentReports and attach the HTML reporter
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// Set system information for the report
		extent.setSystemInfo("Automation Tester", "Lovepreet Pal");
		extent.setSystemInfo("Organization", "PeopleGrove");
		extent.setSystemInfo("Build no", "PG-001");

		return extent;
	}

	// Method to capture a screenshot and save it with a timestamped file name
	public static void captureScreenshot() throws IOException {
		Date d = new Date();
		fileName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		// Capture a screenshot of the WebDriver and save it to the specified directory
		File screenshot = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File(".//reports//Failure_Screenshot//" + fileName));
	}

	// Method to capture a screenshot of a WebElement and save it with a timestamped
	// file name
	public static void captureElementScreenshot(WebElement element) throws IOException {
		Date d = new Date();
		String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		// Capture a screenshot of the WebElement and save it to the specified directory
		File screenshot = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File(".//screenshot//Failure_Screenshot" + "Element_" + fileName));
	}

}