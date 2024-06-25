package com.Mohs10.TestScripts;

import java.util.Properties;
import org.testng.annotations.Test;
import com.Mohs10.Base.ScreenRecorderUtil;
import com.Mohs10.Base.StartBrowser;
import com.Mohs10.Functions.CommonFuns;
import com.Mohs10.utility.Log;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

public class LoginandOrderitem extends StartBrowser
{
	Properties prop;
	String excelfile = "C:\\Users\\Dell\\Desktop\\Deepika\\Grid\\pAInITe\\ExcelTestInputData\\TestData.xlsx";
	String excelsheet = "Login";
	
	@Test(priority = 0 ,description="Login and order products", groups = "Smoke")
	@Severity(SeverityLevel.NORMAL)    
    @Description("User Login and order the products from demo webshop test........")
    @Epic("EP004")
    @Feature("Feature1: ordering a item by successful login")
    @Story("Story:Login and order products from the website")
    @Step("First login and select the product and place the order")
	
	//Login and Order items
	public void LoginandOrderprod() throws Throwable {
		ScreenRecorderUtil.startRecord("Verify Logo");
		Log.startTestCase("Login and Order products");
		CommonFuns cnf = new CommonFuns();
		cnf.OrderProd(null, null);
		ScreenRecorderUtil.stopRecord();
	}

}
