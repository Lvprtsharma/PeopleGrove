package testcases;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.CommonUtil;

public class Assignment2 extends CommonUtil {

	List<String> clickedElementTexts;
	List<WebElement> careerLinks;

	@Test(priority = 1, dependsOnGroups =  {"Login"}, groups = {"Assignment"})
	public void CareerPageCheck() throws InterruptedException {
		try {
			System.out.println("======================== Assignment 2 Started ========================");
			
			click("careerArrow_XPATH");		//Click on Career Arrow
			System.out.println("Clicked on Career Arrow!!");
			
			click("careerPath_XPATH");		//CLick on Career Path
			System.out.println("Clicked on Career Path!!");
			
		} catch (NoSuchElementException e) {	//If no element found forcefully fail whole script
			Assert.fail("Forcefully failing the test as Not able to reach Career Paths Page");
		}
	}

	@Test(priority = 2, dependsOnMethods = "CareerPageCheck", groups = {"Assignment"})
	public void InspirationPathCheck() throws InterruptedException {
		try {	
			System.out.println("Inspiration Path Check!!");
			clickedElementTexts = new ArrayList<>();

			for (int i = 0; i < 3; i++) { // loop to iterate over job links in a sequence
				try {
					careerPathSelect(i);			//Calling the method to click and store career paths
				} catch (StaleElementReferenceException e) {
					careerPathSelect(i);			//Calling the method in case of stale exception
				}
			}
			System.out.println("Text of clicked elements: " + clickedElementTexts);
		} catch (Exception e) {		//If any othe exception found, Forcefully fail whole script
			Assert.fail("Unable to perform Inspiration Path check!!");
		}
	}

	@Test(priority = 3, dependsOnMethods = "CareerPageCheck", groups = {"Assignment"})
	public void UpdateCheck() {
		try {
			System.out.println("Update Page Check!!");
			click("homeBtn_XPATH");				//Clicking on HomeButton
			System.out.println("Clicked on Home Buttons!!");
			
			Thread.sleep(800);
			click("updateBtn_XPATH");			//Clicking on Update Button
			System.out.println("Clicked on Update Option!!");
			
			String currentWindow = driver.getWindowHandle();
			switchWindow();
			System.out.println("Switching to Popup Window!!");

			//Scrolling to the third option in pop-up
			CommonUtil.scroll("3rdOption_XPATH", 0, 0);	
			System.out.println("Scrolled to check 3rd option!!");
			
			boolean isSelected = isElementDisplayed("3rdOption_XPATH");

			//Checking if third option is already selected or not
			if (isSelected) {						
				System.out.println("The radio button is selected for third option!!");
				click("skipPopUp_XPATH");
				System.out.println("Skipping the Popup WIndow!!");
			} else {
				System.out.println("The radio button is not selected for third option!!");
				click("3rdOption_XPATH");
				System.out.println("Choosing the third option!!");
				
				click("updatePopUp_XPATH");
				System.out.println("Clicking on Update Option!!");
				
			}
			click("closePopUp_XPATH");
			driver.switchTo().window(currentWindow);
			System.out.println("Switching to main window!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 4, dependsOnMethods = { "CareerPageCheck", "InspirationPathCheck" }, groups = {"Assignment"})
	public void RecentlyViewCheck() throws InterruptedException {
		try {
			driver.navigate().refresh();
			click("careerArrow_XPATH");		//Click on Career Arrow
			System.out.println("Clicked on Career Arrow!!");
			
			click("careerPath_XPATH");		//CLick on Career Path
			System.out.println("Clicked on Career Path!!");
			
			CommonUtil.scroll("recentlyViewedLink_XPATH", 0, 0); //Scrolling to recently viewed
			System.out.println("Scrolling to Recently Reviewed!!");
			
			String review = OR.getProperty("recentlyViewedCareers_XPATH");
			List<String> reviewedCourses = new ArrayList<>();
			
			for (int i = 1; i < 4; i++) {
				reviewedCourses.add(driver.findElement(By.xpath(review + "[" + i + "]")).getText());
			}

			//Printing the outcomes of clicked elements
			System.out.println("Headings of reviewed courses: " + reviewedCourses);	

			boolean isReversed = true;
			int size = clickedElementTexts.size();

			for (int i = 0; i < size; i++) {
				if (!clickedElementTexts.get(i).equals(reviewedCourses.get(size - 1 - i))) {
					isReversed = false;
					break;
				}
			}
			
			//Verifying if the list is in reversed order of click or not
			if (isReversed) {
				System.out.println("The recently reviewed List is reversed compared to originalList.");
			} else {
				System.out.println("The recently reviewed List is not reversed compared to originalList.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("======================== Assignment 2 Finshed ========================");
	}

	//Method to select the career links
	public void careerPathSelect(int i) throws InterruptedException {

		driver.navigate().refresh();
		CommonUtil.scroll("inspiration_XPATH", 0, 0);
		Thread.sleep(1000);					//Script is running very fast, these sleeps are just to slow down for clear observation
		
		careerLinks = elementSize("inspirationOptions_XPATH"); // counting the size of job links
		careerLinks.get(i).click();
		Thread.sleep(1000); 				//Script is running very fast, these sleeps are just to slow down for clear observation
		
		clickedElementTexts.add(getText("selectedCareer_XPATH"));
		System.out.println(clickedElementTexts+" is Selected!!");
		driver.navigate().back();
	}
}