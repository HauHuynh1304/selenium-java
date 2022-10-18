package com.project.auto_testing.helpers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.auto_testing.common.Actions;
import com.project.auto_testing.enums.BrowserTypes;
import com.project.auto_testing.enums.KeyboardEnums;
import com.project.auto_testing.enums.StatusEnums;
import com.project.auto_testing.enums.WebElementTypes;
import com.project.auto_testing.models.TestCase;

@Component
public class ActionHelper {

	private WebDriver driver;
	private Wait<WebDriver> wait;

	@Autowired
	private InitBrowserHelper browserHelper;

	public void excuteAction(TestCase testCase) throws InterruptedException {
		if (StringUtils.isNotBlank(testCase.getUrl()) && testCase.getAction().equals(Actions.NAVIGATE)) {
			driver = browserHelper.initBrowser(BrowserTypes.CHROME, testCase.getUrl(), testCase.getMaxSleepTime());
			wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofMillis(Math.round(testCase.getMaxSleepTime())))
					.pollingEvery(Duration.ofMillis(5000)).ignoring(NoSuchElementException.class);
			testCase.setActualResult(driver.getCurrentUrl());
			testCase.setStatus(
					VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
		} else if (StringUtils.isNotBlank(testCase.getLocateType())) {
			try {
				By locator = LocatorHelper.locator(testCase.getLocateType(), testCase.getLocateValue());
				// Setting hard delay-time for special Test Cases
				if (ObjectUtils.isNotEmpty(testCase.getMaxSleepTime())) {
					Thread.sleep(Math.round(testCase.getMaxSleepTime()));
				}
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				WebElement el = driver.findElement(locator);
				switch (testCase.getAction()) {
				case Actions.SEND_KEY:
					el.sendKeys(testCase.getAgrument());
					testCase.setActualResult(el.getAttribute(WebElementTypes.VALUE.getValue()));
					testCase.setStatus(
							VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
					break;
				case Actions.CLICK_NAVIGATE:
					String preUrl = driver.getCurrentUrl();
					el.click();
					WaitHelper.getUrlAfterNavigate(preUrl, driver);
					testCase.setActualResult(driver.getCurrentUrl());
					testCase.setStatus(
							VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
					break;
				case Actions.CHECK_MESSAGE:
					testCase.setActualResult(el.getText());
					testCase.setStatus(
							VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
					break;
				case Actions.CLICK:
					el.click();
					testCase.setStatus(StatusEnums.OK.getValue());
					break;
				case Actions.ENABLE:
					String status = String.valueOf(el.isEnabled());
					testCase.setActualResult(status);
					testCase.setStatus(
							VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
					break;
				case Actions.CLEAN_VALUE:
					// Use CTRL A
					// Select the whole value, then delete it
					el.sendKeys(Keys.CONTROL, KeyboardEnums.A.getValue(), Keys.BACK_SPACE);
					testCase.setActualResult(el.getAttribute(WebElementTypes.VALUE.getValue()));
					testCase.setStatus(VerifyStatusHelper.verifyNullResult(testCase.getActualResult()));
					break;
				case Actions.GET_TEXT:
					// Use for switching hover between elements
					testCase.setActualResult(el.getText());
					testCase.setStatus(
							VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
					break;
				case Actions.HOVER_ON:
					org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(
							driver);
					action.moveToElement(el).perform();
					testCase.setStatus(StatusEnums.OK.getValue());
					break;
				case Actions.DISPLAYED:
					testCase.setActualResult(String.valueOf(el.isDisplayed()));
					testCase.setStatus(
							VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
					break;
				case Actions.BEFORE_CLICK_DROPDOWN:
				case Actions.AFTER_CLICK_DROPDOWN:
					// The trick is checking CSS properties
					testCase.setActualResult(el.getAttribute(WebElementTypes.CLASS.getValue()));
					testCase.setStatus(
							VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
					break;
				case Actions.GET_LIST_VALUE_DROPDOWN:
					List<WebElement> els = driver.findElements(locator);
					ArrayList<String> texts = new ArrayList<>();
					for (WebElement element : els) {
						texts.add(element.getText());
					}
					String lastValue = texts.toString();
					testCase.setActualResult(lastValue.substring(1, lastValue.length() - 1));
					testCase.setStatus(
							VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
					break;
				case Actions.SELECT_VALUE:
					Select select = new Select(driver.findElement(locator));
					select.selectByVisibleText(testCase.getExpectedResult());
					String selectedValue = select.getFirstSelectedOption().getText();
					testCase.setActualResult(selectedValue);
					testCase.setStatus(
							VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
					break;
				case Actions.GET_TYPE:
					testCase.setActualResult(el.getAttribute(WebElementTypes.TYPE.getValue()));
					testCase.setStatus(
							VerifyStatusHelper.verifyStatus(testCase.getExpectedResult(), testCase.getActualResult()));
					break;
				default:
					break;
				}
			} catch (NoSuchElementException | StaleElementReferenceException | TimeoutException
					| UnexpectedTagNameException | NullPointerException e) {
				testCase.setActualResult(ExceptionUtils.getRootCauseMessage(e));
				testCase.setStatus(StatusEnums.NG.getValue());
			}

		} else if (!ObjectUtils.allNull(testCase) && Actions.CLOSE_BROWSER.equals(testCase.getAction())) {
			driver.close();
		} else {

		}
	}

}
