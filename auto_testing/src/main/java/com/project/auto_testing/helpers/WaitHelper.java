package com.project.auto_testing.helpers;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.project.auto_testing.models.TestCase;

public class WaitHelper {

	/*
	 * Waiting for catch web element
	 */
	public static Wait<WebDriver> setFluentWait(TestCase testCase, WebDriver driver) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofMillis(Math.round(testCase.getMaxSleepTime())))
				.pollingEvery(Duration.ofMillis(1000)).ignoring(NoSuchElementException.class);
		return wait;
	}

	/*
	 * Waiting for catch new URL after navigate
	 */
	public static String getUrlAfterNavigate(String preUrl, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(10000));
		ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return (!d.getCurrentUrl().equals(preUrl));
			}
		};
		wait.until(e);
		return driver.getCurrentUrl();
	}

}
