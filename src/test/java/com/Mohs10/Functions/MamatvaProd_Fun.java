package com.Mohs10.Functions;

import org.openqa.selenium.WebDriver;

import com.Mohs10.Base.Action;
import com.Mohs10.Base.PropertyFile;
import com.Mohs10.Base.StartBrowser;
import com.Mohs10.OR.MamatvaProd_OR;

public class MamatvaProd_Fun {

	public static Action aDriver;
    public WebDriver driver;
    public String appUrl; // Declare appUrl as a class member variable

    public MamatvaProd_Fun() {
        
    	aDriver = new Action();
		driver = StartBrowser.driver;
		
		// Initialize appUrl in the constructor
       try {
            appUrl = PropertyFile.getValueForKey("url");
        } catch (Throwable e) {
            e.printStackTrace();
        } 
	}


    // *****************************************************Textbooks Script*******************************************************//
    
    
    public void Login(String email, String password) throws Exception {
        StartBrowser.childTest = StartBrowser.parentTest.createNode("Contacts page of salesforce application");
        aDriver.navigateToApplication("http://productionmamatva.ap-south-1.elasticbeanstalk.com/");
        aDriver.type(MamatvaProd_OR.Email, email, "type email");
        Thread.sleep(4000);
        
        aDriver.type(MamatvaProd_OR.Password, password, "type password");
        aDriver.click(MamatvaProd_OR.Loginbtn, "click on login btn");
        aDriver.click(MamatvaProd_OR.Logoutbtn, "click on logout btn");
        
    }
	
	
	
}
