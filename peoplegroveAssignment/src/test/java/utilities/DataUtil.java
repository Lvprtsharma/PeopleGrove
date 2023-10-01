package utilities;

import org.testng.annotations.DataProvider;

import base.BaseTest;

public class DataUtil extends BaseTest {
	
	@DataProvider(name = "jobPage")
	public Object[][] getSignUpData() {

		return new Object[][] {
            { "Testing Assignment 1", "Total experience 4 years","You'll get an email when * responds."}};
	}

	@DataProvider(name = "inboxPage")
	public Object[][] getJobPage() {

		return new Object[][] {
			{"You reached out to * for help with a job!" }};
	}
}