package com.project.auto_testing.helpers;

import org.openqa.selenium.By;

import com.project.auto_testing.common.LocationTypes;

public class LocatorHelper {

	public static By locator(String locateType, String locateValue) {
		By webElement = null;
		switch (locateType) {
		case LocationTypes.ID:
			webElement = By.id(locateValue);
			break;
		case LocationTypes.NAME:
			webElement = By.name(locateValue);
			break;
		case LocationTypes.XPATH:
			webElement = By.xpath(locateValue);
			break;
		default:
			break;
		}
		return webElement;
	}
}
