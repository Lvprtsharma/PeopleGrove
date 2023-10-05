package testcases;

import org.testng.annotations.Test;

import utilities.CommonUtil;

public class LogOut extends CommonUtil {

	@Test(groups = {"Logout"})
	// Method to perform a logout check
	public void LogOutCheck() {
		try {
			System.out.println("LogOut Started!!");
			// Hover over the profile element
			mouseHover("profileHover_XPATH");
			System.out.println("Mouse Hovered to Profile Option");

			// Click the sign-out button
			click("signOut_XPATH");
			System.out.println("SignOut Option Clicked!!");

			// Check if the sign-up element is displayed 
			if (isElementDisplayed("signinBtn_XPATH")) {
				System.out.println("Logged Out Successfully!!!");
			}
		} catch (Exception e) {
			// Handle any exceptions by printing the stack trace
			e.printStackTrace();
		}
	}
}
