package com.project.auto_testing.helpers;

import org.openqa.selenium.By;

import com.project.auto_testing.common.LocationTypes;

public class LocatorHelper {

	public static By locator(String locateType, String locateValue) {
		By by = null;
		switch (locateType) {
		case LocationTypes.ID:
			by = By.id(locateValue);
			break;
		case LocationTypes.NAME:
			by = By.name(locateValue);
			break;
		case LocationTypes.XPATH:
			by = By.xpath(locateValue);
			break;
		case LocationTypes.CLASS:
			by = By.className(locateValue);
			break;
		default:
			break;
		}
		return by;
	}

}
