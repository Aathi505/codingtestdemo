package com.eggtimer.tests;

import java.util.stream.IntStream;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.eggtimer.pages.CountdownTimerPage;
import com.qa.driver.base.TestBase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CountdownTimerTest extends TestBase{

	CountdownTimerPage timerPage;
	public static String counterStart;
	
	public CountdownTimerTest() {
		super();
	}
	
	@BeforeClass
	public void setup() {
		initializeDriver();
		timerPage = new CountdownTimerPage();
	}
	
	@Test(priority = 0)
	@Parameters("startTime")
	public void startCounter(String startTime) {
		counterStart=startTime;
		timerPage.startTimer(startTime);
	}
	
	//@Then("user verify that counter value decreases every second in Web Page")
	@Test(priority = 1)
	public void verifyCounter() {
		int counter = Integer.parseInt(counterStart);
		
		for(int count=counter-1;count>0;count--) {
			String expectedCount = String.valueOf(count);
			Assert.assertTrue(timerPage.verifyCurrentCountdown(expectedCount));
			Reporter.log("Expected Count "+expectedCount+" seconds matches with Actual");
		}
		
	}
	
	
	//@Then("user verify alert {string}")
	@Test(priority = 2)
	@Parameters("alertText")
	public void verifyAlert(String alertText) {
		Assert.assertEquals(timerPage.getAlertText(), alertText);
		Reporter.log("Alert Text "+alertText+" is displayed successfully");
		timerPage.acceptAlert();
	}
	
	@AfterClass
	public void teardown() {
		driver.quit();
	}
}
