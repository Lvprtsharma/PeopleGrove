package testcases;

import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.CommonUtil;
import utilities.DataUtil;

public class Assignment1 extends CommonUtil {

	String firstName;
	String message1;
	String message2;
	String inboxMessagefromDP;

	@Test(priority = 1, dependsOnGroups =  {"Login"}, groups = {"Assignment"})
	public void HomePageCheck() {
		try {
			System.out.println("======================== Assignment 1 Started ========================");
			click("careerArrow_XPATH"); // Selecting the career option
			System.out.println("Career Arrow clicked!!");
			
			click("jobs_XPATH"); // Selecting the jobs option
			System.out.println("Job option Clicked!!");
		} catch (StaleElementReferenceException e) {
			System.out.println("Element is not visible");
		}
	}

	@Test(priority = 2, dependsOnMethods = "HomePageCheck", groups = {"Assignment"})
	public void AskQuestionCheck() throws InterruptedException {
		try {
			while (isElementDisplayed("jobLoader_XPATH", 2)) { // Check if loader is available then load all job links
				scroll("jobLoader_XPATH", 0, 0);
				System.out.println("Job Loader Found and Scrolled Down!!");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("All Job links loaded successfully!!");
		}
		try {
			List<WebElement> joblinks = elementSize("jobLinks_XPATH"); // counting the size of job links
			boolean checkAskQuestions = true;

			for (int i = 0; i < joblinks.size(); i++) { // loop to iterate over job links in a sequence
				try {
					joblinks.get(i).click();
					System.out.println("Checking on : " + joblinks.get(i).getText());
					if (isElementDisplayed("jobProfileName_XPATH", 1)) { // Checking if Profile name is available
						scroll("personCard_XPATH", 0, 0);
					} else {
						System.out.println("Recruiter Name is not available!!");
						continue;
					}
					if (isElementDisplayed("askQuestion_XPATH", 1)) { // Checking if Ask Question option is available
						System.out.println("Ask Question Option Available!!");
						checkAskQuestions = false;
						break;
					} else {
						System.out.println("No Ask Question Option Available!!");
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (checkAskQuestions) {
				System.out.println("All options of Asking Questions have been used!!!");
				Assert.fail("Forcefully failing the test as No Ask Question Option is Available to proceed further!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 3, dependsOnMethods = "AskQuestionCheck", dataProviderClass = DataUtil.class, dataProvider = "jobPage", groups = {"Assignment"})
	public void JobsPageCheck(String messageLine1, String messageLine2, String confirmationMessage) {
		try {
			message1 = messageLine1;
			message2 = messageLine2;
			
			click("askQuestion_XPATH");
			System.out.println("Ask Question option found and clicked!!");

			type("typeSpace_XPATH", message1);
			System.out.println("Typed message1 as : "+message1);
			enterClick("typeSpace_XPATH");
			
			type("typeSpace_XPATH", message2);
			System.out.println("Typed message2 as : "+message2);
			
			click("sendBtn_XPATH");
			System.out.println("Clicked on Send Button!!");
			
			String messageFromUI = getText("messageSent_XPATH");
			firstName = getText("jobProfileName_XPATH").split(" ")[0];
			System.out.println("Recruiter FIrst Name extracted from UI : "+firstName);
			
			String confirmMessagefromDP = confirmationMessage;
			confirmMessagefromDP = confirmMessagefromDP.replace("*", firstName);
			Assert.assertEquals(messageFromUI, confirmMessagefromDP, "Both Messages got matched");

			scroll("keepConvoGoing_XPATH", 0, 0);
			click("keepConvoGoing_XPATH");
			System.out.println("Clicked on Keep Convo Going Option!!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 4, dependsOnMethods = "JobsPageCheck", dataProviderClass = DataUtil.class, dataProvider = "inboxPage", groups = {"Assignment"})
	public void InboxPageCheck(String inboxMsg) {
		try {
			String informessagefromUI = getText("infoTitle_XPATH");
			System.out.println("Message shown on Inbox as : "+informessagefromUI);
			
			inboxMessagefromDP = inboxMsg.replace("*", firstName);
			System.out.println("After inserting Firstname in the expected Message : "+inboxMessagefromDP);

			Assert.assertTrue(informessagefromUI.equals(inboxMessagefromDP), "First Name is contained in message");

			String firstline = getText("firstLine_XPATH");
			System.out.println("First Line of message sent to Recruiter as : "+firstline);
			
			String secondline = getText("secondLine_XPATH");
			System.out.println("Second Line of message sent to Recruiter as : "+secondline);
			
			Assert.assertTrue(firstline.equals(message1), "first line matched");
			Assert.assertTrue(secondline.equals(message2), "second line matched");

			click("viewProfile_XPATH");
			click("profileView_XPATH");
			System.out.println("Clicked on Profile Name!!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 5, dependsOnMethods = "InboxPageCheck", groups = {"Assignment"})
	public void RecrtuierPageCheck() {
		try {
			CommonUtil.scroll("otherSimilars_XPATH", 0, 0);
			System.out.println("Scrolled to Other Similar Options!!");
			
			List<WebElement> otherSimilar = elementSize("similarNames_XPATH");
			System.out.println("Total Number of Other Similars : "+otherSimilar.size());
			for (WebElement similar : otherSimilar) {
				System.out.println("Other Similars are as : " + similar.getText());
			}
			click("backBtn_XPATH");
			System.out.println("Clicked on Back Button!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("======================== Assignment 1 Finished ========================");
	}
}