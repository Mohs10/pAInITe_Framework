package com.Mohs10.TestScripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
 
public class Sample_TC_forbrowsername {
 
    public static void main(String[] args) {
        // Set the system property for the WebDriver executable (e.g., chromedriver or geckodriver)
//        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
//        System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
 
        // Instantiate ChromeDriver
        //WebDriver driver = new ChromeDriver();
 
        // Alternatively, instantiate FirefoxDriver
       // WebDriver driver = new FirefoxDriver();
 
        WebDriver driver = new EdgeDriver();
        // Fetch browser name
        String browserName = getBrowserName(driver);
 
        // Output the browser name
        System.out.println("Browser Name: " + browserName);
 
        // Close the browser
        driver.quit();
    }
 
    // Helper method to fetch browser name
    private static String getBrowserName(WebDriver driver) {
        String browserName = "Unknown";
 
        if (driver instanceof ChromeDriver) {
            browserName = "Chrome";
        } else if(driver instanceof EdgeDriver) {
            browserName = "Edge";
        } else if (driver instanceof FirefoxDriver) {
            browserName = "Firefox";
        } else {
            // For other browsers, try to fetch browser name from capabilities
            try {
                DesiredCapabilities capabilities = (DesiredCapabilities) ((ChromiumDriver) driver).getCapabilities();
                if (capabilities != null && capabilities.getCapability(CapabilityType.BROWSER_NAME) != null) {
                    browserName = capabilities.getCapability(CapabilityType.BROWSER_NAME).toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
 
        return browserName;
    }
}
