package com.Mohs10.Functions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.Mohs10.Base.Action;
import com.Mohs10.Base.PropertyFile;
import com.Mohs10.Base.StartBrowser;
import com.Mohs10.OR.HomePage;

public class CommonFuns {
	public Action aDriver;
	public WebDriver driver;
	public String appUrl; // Declare appUrl as a class member variable
	
	public CommonFuns() {
		aDriver = new Action();
		driver = StartBrowser.driver;
		
		// Initialize appUrl in the constructor
       try {
            appUrl = PropertyFile.getValueForKey("url");
        } catch (Throwable e) {
            e.printStackTrace();
        } 
	}

	// Login process
	public void logIn(String email, String pwd) throws Throwable {
		StartBrowser.childTest = StartBrowser.parentTest.createNode("Login to DemoWeb shop");

	    // Navigate to the application using the URL from the properties
	    aDriver.navigateToApplication(appUrl);

		aDriver.click(HomePage.lnkLogin, "Login link");
		aDriver.type(HomePage.txtemail, email, "email text box");
		aDriver.type(HomePage.txtpwd, pwd, "password text box");
		aDriver.click(HomePage.btnlogin, "Login button");
		aDriver.click(HomePage.lnkLogout, "Logout link");
	}

	// Negative login process
	public void invalidLogIn(String email, String pwd) throws Exception {
		StartBrowser.childTest = StartBrowser.parentTest.createNode("Login to DemoWeb shop with invalid credentials");
		  aDriver.navigateToApplication(appUrl);
	/*	aDriver.click(HomePage.lnkLogin, "Login link");
		aDriver.type(HomePage.txtemail, email, "email text box");
		aDriver.type(HomePage.txtpwd, pwd, "password text box");
		aDriver.click(HomePage.btnlogin, "Login button"); */
	}

	public void logout() throws Exception {

		aDriver.click(HomePage.lnkLogout, "Logout link");
	}

	// Registration process
	public void Register(String Firstname, String Lastname, String Email, String Pwd, String ConfirmPwd)
			throws Exception {
		StartBrowser.childTest = StartBrowser.parentTest.createNode("Register in DemoWeb shop");
		  aDriver.navigateToApplication(appUrl);

		aDriver.click(HomePage.lnkRegister, "Register link");

		aDriver.click(HomePage.rbGender, "Radio button Gender");

		aDriver.type(HomePage.txtFirstname, Firstname, "FirstName text box");
		aDriver.type(HomePage.txtLastname, Lastname, "LastName text box");
		aDriver.type(HomePage.txtEmail, Email, "Email text box");
		aDriver.type(HomePage.txtPassword, Pwd, "password text box");
		aDriver.type(HomePage.txtConfirmpassword, ConfirmPwd, "confirm password text box");
		aDriver.click(HomePage.btnRegister, "Register button");
		aDriver.click(HomePage.btnContinue, "Continue button");
		aDriver.click(HomePage.lnkLogout, "Logout link");
	}

	// Login and order a product
	public void OrderProd(String email, String pwd) throws Throwable {
		StartBrowser.childTest = StartBrowser.parentTest.createNode("Category links");
		  aDriver.navigateToApplication(appUrl);
		aDriver.click(HomePage.lnkLogin, "Login link");
		aDriver.type(HomePage.txtemail, email, "email text box");
		aDriver.type(HomePage.txtpwd, pwd, "password text box");
		aDriver.click(HomePage.btnlogin, "Login button");
		aDriver.click(HomePage.lnkApparelandShoes, "Performed click operation on Apparel and Shoes Link");
		aDriver.click(HomePage.frokitem, "click on frok item");
		Action.implicitWait(driver, 10);
		aDriver.click(HomePage.btnAddtoCart, "click on add to cart button");
		aDriver.click(HomePage.lnkShoppingCart, "click on shoppingcart to order");
		aDriver.click(HomePage.acceptterms, "accept terms ceckbox");
		aDriver.click(HomePage.btnCheckout, "click on checkout button");
		aDriver.click(HomePage.btnCntnue1, "click on continue button after address");
		aDriver.click(HomePage.btnBillingContinue, "");
		aDriver.click(HomePage.btnShippingContinue, "");
		aDriver.click(HomePage.btnShpngMthdContinue, "");
		aDriver.click(HomePage.btnPaymentMthdContinue, "");
		aDriver.click(HomePage.btnPaymentInfoContinue, "");
		aDriver.click(HomePage.Confirmorder, "");
		aDriver.click(HomePage.ordercomplete, "");
		aDriver.click(HomePage.lnkLogout, "Logout link");
	}

	// Verify the website title and logo- index page
	public void VerifyingWebsiteTitle() {
		StartBrowser.childTest = StartBrowser.parentTest.createNode("Verifying the Title of the Application");
		  aDriver.navigateToApplication(appUrl);

		String actualTitle = driver.getTitle();
		String expTitle = "Demo Web Shop";

		if (actualTitle.equalsIgnoreCase(expTitle)) {
			System.out.println("Verified Title : Test Passed   " + actualTitle);
		} else {
			System.out.println("Title Not Matched : Test Failed" + expTitle);
		}
	}

	// Identify Logo
	public void VerifyingLogo() {
		StartBrowser.childTest = StartBrowser.parentTest.createNode("Verifying the Logo of the Application");
		  aDriver.navigateToApplication(appUrl);

		boolean logopresent = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div[1]/a/img")).isDisplayed();
		Assert.assertTrue(logopresent);
	}

	// News button
	public void Pagedown() throws Exception

	{
		StartBrowser.childTest = StartBrowser.parentTest.createNode("Page down");
		  aDriver.navigateToApplication(appUrl);
		Actions a = new Actions(driver);
		a.sendKeys(Keys.PAGE_DOWN).perform();
		a.sendKeys(Keys.PAGE_DOWN).perform();
	}

	public void Newsbtn() throws Exception {
		StartBrowser.childTest = StartBrowser.parentTest.createNode("News button");
		  aDriver.navigateToApplication(appUrl);
		aDriver.click(HomePage.btnNews, "Performed click operation on News button");
	}

	// Blog Link
	public void Blogbtn() throws Exception {
		StartBrowser.childTest = StartBrowser.parentTest.createNode("Blog buttton");
		  aDriver.navigateToApplication(appUrl);
		aDriver.click(HomePage.btnBlog, "Performed click operation on Blog button");
	}

	// Categories
	public void Categories() throws Exception {
		StartBrowser.childTest = StartBrowser.parentTest.createNode("Category links");
		  aDriver.navigateToApplication(appUrl);
		aDriver.click(HomePage.lnkBooks, "Performed click operation on books link");
		aDriver.click(HomePage.lnkComputer, "Performed click operation on Computer Link");
		aDriver.click(HomePage.lnkElectronics, "Performed click operation on Electronics Link");
		aDriver.click(HomePage.lnkApparelandShoes, "Performed click operation on Apparel and Shoes Link");
		aDriver.click(HomePage.lnkDigitaldownloads, "Performed click operation on Digital Downloads Link");
		aDriver.click(HomePage.lnkJewelry, "Performed click operation on Jewelry Link");
		aDriver.click(HomePage.lnkGiftCards, "Performed click operation on Gift Cards Link");
	}

	public void DemoLoginforReports() {
		// TODO Auto-generated method stub
		
	}	
	
	// AutoIT Reuse functions
//		  public void uploadWordFile() throws Exception
//			{
//				try {
//
//				StartBrowser.childTest = StartBrowser.parentTest.createNode("Automation using Autoit tool");
//				aDriver.navigateToApplication("https://www.ilovepdf.com/word_to_pdf");
//				aDriver.click(AllPageObjects.selectwordfile, "Wordtopdf convert btn");
//
//				Runtime.getRuntime().exec("Fileupload.exe");
//
//				aDriver.click(AllPageObjects.Converttopdf, "Convert btn");
//
//				Thread.sleep(3000);
//				aDriver.click(AllPageObjects.downloadbtn, "Download the converted file");
//
//				}
//				catch (StaleElementReferenceException e) {
//
//				}
//				finally {
//					Runtime.getRuntime().exec("Filedownload.exe");
//				}
//
//				Thread.sleep(5000);
//			}
//

}
