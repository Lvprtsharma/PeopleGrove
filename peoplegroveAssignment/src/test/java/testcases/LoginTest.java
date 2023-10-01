package testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utilities.CommonUtil;

public class LoginTest extends CommonUtil {

	@Test(priority = 1, groups = {"Login"})
	@Parameters({ "username", "password" })
	public void Login(String username, String password) {
		try {
			System.out.println("======================== Logging Started ========================");
			// Click the sign-in button
			click("signinBtn_XPATH");
			System.out.println("Clicked on SignIn Option!!");
			
			// Type the username into the email input field 
			type("email_XPATH", username);
			System.out.println("Username Entered : "+username);

			// Type the password into the password input field 
			type("password_XPATH", password);
			System.out.println("Password Entered : "+password);
			
			// Click the login button 			
			click("loginBtn_XPATH");
			System.out.println("Clicked on Login Button");
			
			if(elementDisplayed("homeBtn_XPATH")) {
				System.out.println("======================== Logged in Successfully ========================");
			}else {
				Assert.fail("Login Unsuccessfull!!");
				driver.quit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}