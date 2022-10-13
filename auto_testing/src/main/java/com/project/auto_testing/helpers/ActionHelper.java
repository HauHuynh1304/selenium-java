package com.project.auto_testing.helpers;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.auto_testing.common.Actions;
import com.project.auto_testing.enums.BrowserTypes;
import com.project.auto_testing.enums.WebElementTypes;
import com.project.auto_testing.models.TestCase;

@Component
public class ActionHelper {

	private WebDriver driver;

	@Autowired
	private InitBrowserHelper browserHelper;

	public void action(TestCase testCase) throws InterruptedException {
		if (StringUtils.isNotBlank(testCase.getUrl()) && testCase.getAction().equals(Actions.NAVIGATE)) {
			driver = browserHelper.initBrowser(BrowserTypes.CHROME, testCase.getUrl(), testCase.getMaxSleepTime());
			testCase.setActualResult(driver.getCurrentUrl());
			testCase.setStatus(
					VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
		} else if (StringUtils.isNotBlank(testCase.getLocateType())) {
			By el = LocatorHelper.locator(testCase.getLocateType(), testCase.getLocateValue());
			switch (testCase.getAction()) {
			case Actions.SEND_KEY:
				WaitHelper.setFluentWait(testCase, driver);
				driver.findElement(el).clear();
				driver.findElement(el).sendKeys(testCase.getAgrument());
				testCase.setActualResult(driver.findElement(el).getAttribute(WebElementTypes.VALUE.getValue()));
				testCase.setStatus(
						VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
				break;
			case Actions.CLICK_NAVIGATE:
				WaitHelper.setFluentWait(testCase, driver);
				driver.findElement(el).click();
				WaitHelper.getUrlAfterNavigate(driver.getCurrentUrl(), driver);
				testCase.setActualResult(driver.getCurrentUrl());
				testCase.setStatus(
						VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
				break;
			default:
				break;
			}
		}
	}

}
