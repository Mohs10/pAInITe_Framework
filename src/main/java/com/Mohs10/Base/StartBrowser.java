package com.Mohs10.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.Mohs10.utility.ExtentReport;
import com.Mohs10.utility.Log;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class StartBrowser {

	EdgeOptions edgeOptions = new EdgeOptions();
	FirefoxOptions fireOptions = new FirefoxOptions();
	ChromeOptions chromeOptions = new ChromeOptions();
	public static WebDriver driver;
	public static Properties prop;
	public static Capabilities cap;
	public static String browserName; 
	
	// set up report
	public static ExtentTest parentTest;
	public static ExtentTest childTest;
	public static ExtentReports extent;

	// loadConfig method is to load the configuration
	@BeforeSuite(groups = { "Smoke", "Sanity", "Regression" })
	public void loadConfig() throws Throwable {
		extent = new ExtentReports(); // Initialize the extent variable
		ExtentReport.setExtent();
		
		 // Add browser information to the extent report
	    extent.setSystemInfo("Browser", browserName);

		DOMConfigurator.configure("src\\main\\resources\\log4j2.xml");
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/resources/Config.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void methodName(Method method) {
		
		// Log the browser name
		Log.info("Executing test method: " + method.getName() + " on browser: " + browserName);
		
		// Create a parent test in the extent report with the name of the test method
		parentTest = extent.createTest(method.getName()+ " on " + browserName);
	}

	@BeforeClass
	@Parameters(value = { "browser"})
	public void browserSetup(String browserName) throws Throwable {

		 Log.info("Setting up browser: " + browserName);
		 StartBrowser.browserName = browserName; 

		// Setup WebDriver for browsers
		if (browserName.equalsIgnoreCase("chrome")) {

			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
				cap = ((RemoteWebDriver) driver).getCapabilities();
				// Access the browser information
				System.out.println("Browser Name: " + cap.getBrowserName());				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browserName.equalsIgnoreCase("Firefox")) {

			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), fireOptions);
				cap = ((RemoteWebDriver) driver).getCapabilities();
				System.out.println("Browser Name: " + cap.getBrowserName());
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browserName.equalsIgnoreCase("Edge")) {
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/"), edgeOptions);
				cap = ((RemoteWebDriver) driver).getCapabilities();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		// Handle other browsers or invalid browser names
		else {
			Reporter.log("Invalid browser name provided: " + browserName, true);
			return;
		}

		// Maximize the browser window
		driver.manage().window().maximize();
		// Delete all the cookies
		driver.manage().deleteAllCookies();
		// Set Implicit TimeOuts
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// Set PageLoad TimeOuts
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
	}

	@AfterMethod
	public void tearDown() {
		Log.info("Tearing down the WebDriver");
		// Close the WebDriver instance
		if (driver != null) {
			driver.quit();
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
		extent.flush();

	}

	@AfterSuite(groups = { "Smoke", "Regression", "Sanity" })
	public void afterSuite() {
		ExtentReport.endReport();
		Log.info("This is After Suite Method");
	}
}
