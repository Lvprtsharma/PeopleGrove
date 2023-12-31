package testcases;

import org.openqa.selenium.StaleElementReferenceException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utilities.CommonUtil;

public class SignUpTest extends CommonUtil {

	public int randomNum = RandomNumberGenerator();

	@Test(priority = 1, groups = { "Signup" })
	public void signUp_Selction() {
		try {
			System.out.println("======================== Signup Started ========================");
			click("signUp_XPATH");
			click("signupWithEmail_XPATH");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 2, groups = { "Signup" })
	@Parameters({ "firstname", "lastname", "username", "password" })
	public void signUpDetails(String firstname, String lastname, String username, String password) {
		try {
			type("firstName_XPATH", firstname);
			System.out.println("First Name : " + firstname);

			type("lastName_XPATH", lastname);
			System.out.println("Last Name : " + lastname);

			type("email_XPATH", username + randomNum + "@peoplegrove.com");
			System.out.println("Username : " + username + randomNum + "@peoplegrove.com");

			type("password_XPATH", password);
			System.out.println("Password : " + password);

			click("agreePolicy_XPATH");
			click("createAccount_XPATH");

			if (isElementDisplayed("accountExists_XPATH", 1)) {

				System.out.println("This account already exists");
				clear("email_XPATH");
				type("email_XPATH", username + randomNum + 1 + "@peoplegrove.com");

				System.out.println("Retyped Username : " + username + randomNum + 1 + "@peoplegrove.com");
				click("createAccount_XPATH");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 3, groups = { "Signup" })
	public void joiningAs_Selction() {
		try {
			click("students_XPATH");
			System.out.println("Selected : " + getText("students_XPATH"));

			click("nextBtn_XPATH");
			System.out.println("Clicked Next");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 4, groups = { "Signup" })
	public void enjoyDoing_Selection() {
		try {
			click("creatingContent_XPATH");
			System.out.println("Selected : " + getText("creatingContent_XPATH"));

			click("learningLanguage_XPATH");
			System.out.println("Selected : " + getText("learningLanguage_XPATH"));

			click("music_XPATH");
			System.out.println("Selected : " + getText("music_XPATH"));

			click("nextBtn_XPATH");
			System.out.println("Clicked Next");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 5, groups = { "Signup" })
	public void motivates_Selection() throws InterruptedException {
		try {
			click("beingChallenged_XPATH");
			System.out.println("Selected : " + getText("beingChallenged_XPATH"));

			click("beingCreative_XPATH");
			System.out.println("Selected : " + getText("beingCreative_XPATH"));

			click("learningthings_XPATH");
			System.out.println("Selected : " + getText("learningthings_XPATH"));

			click("nextBtn_XPATH");
			System.out.println("Clicked Next");
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 6, groups = { "Signup" })
	public void photo_Selection() {
		try {
			click("laterPlease_XPATH");
			System.out.println("Selected : " + getText("laterPlease_XPATH"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 7, groups = { "Signup" })
	public void career_Selection() {
		try {
			click("option1_XPATH");
			System.out.println("Selected : " + getText("option1_XPATH"));

			click("finish_XPATH");
			System.out.println("Clicked Finish");
			System.out.println("======================== Signup Finished ========================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
