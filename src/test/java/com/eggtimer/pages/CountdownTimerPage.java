package com.eggtimer.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.driver.base.TestBase;

public class CountdownTimerPage extends TestBase {
	WebDriverWait wait;

	@FindBy(id = "EggTimer-start-time-input-text")
	WebElement timerValue_textbox;

	@FindBy(xpath = "//button[text()='Start']")
	WebElement startTimer_button;

	@FindBy(xpath = "//p[@class='ClassicTimer-time']/span")
	WebElement countdownTime;

	public boolean verifyCurrentCountdown(String expectedCount) {
		wait.until(ExpectedConditions.visibilityOf(countdownTime));
		wait.until(ExpectedConditions.textToBePresentInElement(countdownTime, expectedCount));
		return countdownTime.getText().contains(expectedCount);
	}

	public CountdownTimerPage() {
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 10);
	}

	public void startTimer(String startValue) {
		timerValue_textbox.clear();
		timerValue_textbox.sendKeys(startValue);
		startTimer_button.click();
	}

	public String getAlertText() {
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}

	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}
}
