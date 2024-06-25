package com.Mohs10.utility;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CheckSeleniumVersion {
	
	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
        RemoteWebDriver driver = new RemoteWebDriver(options);
        Capabilities capabilities = driver.getCapabilities();
        String version = capabilities.getBrowserVersion();
        System.out.println("Selenium version: " + version);
        driver.quit(); // Close the driver after getting version
    }
}
