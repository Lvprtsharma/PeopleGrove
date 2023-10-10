package testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTest extends SignUpTest {

	@Test(priority = 1, groups = { "Login" })
	@Parameters({ "username", "password" })
	public void Login(String username, String password) {
		try {
			System.out.println("======================== Logging Started ========================");
			// Click the sign-in button
			click("signinBtn_XPATH");
			System.out.println("Clicked on SignIn Option!!");

			// Type the username into the email input field
			type("email_XPATH", username + randomNum + "@peoplegrove.com");
			System.out.println("Username Entered : " + username + randomNum + "@peoplegrove.com");

			// Type the password into the password input field
			type("password_XPATH", password);
			System.out.println("Password Entered : " + password);

			// Click the login button
			click("loginBtn_XPATH");
			System.out.println("Clicked on Login Button");

			if (isElementDisplayed("homeBtn_XPATH", 3)) {
				System.out.println("======================== Logged in Successfully ========================");
			} else {
				Assert.fail("Login Unsuccessfull!!");
				driver.quit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
