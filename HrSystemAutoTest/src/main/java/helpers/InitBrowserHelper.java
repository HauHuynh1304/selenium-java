package helpers;

import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import enums.BrowserTypes;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.OperaDriverManager;

public class InitBrowserHelper {

	public WebDriver initBrowser(BrowserTypes browserTypes, String url, Integer sleep1, Integer sleep2) {
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
		if(StringUtils.isNotBlank(url)) {
			driver.navigate().to(url);
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(sleep1));
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(sleep2));
		return driver;
	}
}
