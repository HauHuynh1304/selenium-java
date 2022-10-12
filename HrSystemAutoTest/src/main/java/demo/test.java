package demo;

import org.openqa.selenium.WebDriver;

import enums.BrowserTypes;
import helpers.InitBrowserHelper;

public class test {
	public static void main(String[] args) {
		String url = "https://www.google.com/?hl=vi";
		WebDriver driver = new InitBrowserHelper().initBrowser(BrowserTypes.EDGE, url, 1000, 1000);
	}
}
