package com.project.auto_testing.helpers;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Service;

import com.project.auto_testing.enums.BrowserTypes;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.OperaDriverManager;

@Service
public class InitBrowserHelper {

	public WebDriver initBrowser(BrowserTypes browserTypes, String url, Double maxSleepTime) {
		WebDriver driver = null;
		switch (browserTypes) {
		case CHROME:
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case FIREFOX:
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case OPERA:
			WebDriverManager.operadriver().setup();
			driver = (WebDriver) new OperaDriverManager();
			break;
		case EDGE:
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default:
			break;
		}
		// waiting to throw exception
		if (StringUtils.isNotBlank(url)) {
			driver.navigate().to(url);
		}

		driver.manage().window().maximize();
		return driver;
	}
}
