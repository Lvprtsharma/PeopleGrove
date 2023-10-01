package extentlisteners;

import java.io.IOException;
import java.util.Date;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentListeners implements ITestListener, ISuiteListener {

	// Create a static Date object for timestamp
	static Date d = new Date();

	// Generate a unique filename for the HTML report
	static String fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

	// Create an instance of ExtentReports for report generation
	private static ExtentReports extent = ExtentManager.createInstance(".\\reports\\" + fileName);

	// Declare a static ExtentTest object
	public static ExtentTest test;

	// Method called when a test starts
	public void onTestStart(ITestResult result) {
		// Create a test instance with the test class name and method name
		test = extent
				.createTest(result.getTestClass().getName() + "     @TestCase : " + result.getMethod().getMethodName());
	}

	// Method called when a test is successful
	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TEST CASE:- " + methodName.toUpperCase() + " PASSED" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		test.pass(m);
	}

	// Method called when a test fails
	public void onTestFailure(ITestResult result) {
		test.fail(result.getThrowable().getMessage());
		try {
			ExtentManager.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TEST CASE:- " + methodName.toUpperCase() + " FAILED" + "</b>";
		test.fail("<b><font color=red>" + "Screenshot of failure" + "</font></b><br>",
				MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.fileName).build());

		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		test.log(Status.FAIL, m);
	}

	// Method called when a test is skipped
	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test Case:- " + methodName + " Skipped" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.AMBER);
		test.skip(m);
	}

	// Method called when a test fails but is within success percentage
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	// Method called when the test suite starts
	public void onStart(ITestContext context) {
		// TODO: Add any setup code if needed
	}

	// Method called when the test suite finishes
	public void onFinish(ITestContext context) {
		// Flush the extent report to save the results
		if (extent != null) {
			extent.flush();
		}
	}

	// Other overridden methods for test suite setup/teardown
	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
	}

}
