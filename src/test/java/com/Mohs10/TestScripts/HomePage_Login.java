package com.Mohs10.TestScripts;

import java.util.Properties;

import org.testng.annotations.Test;

//import com.Mohs10.Base.ScreenRecorderUtil;
import com.Mohs10.Base.StartBrowser;
import com.Mohs10.Base.XLUtils;
import com.Mohs10.Functions.CommonFuns;
import com.Mohs10.utility.Log;

public class HomePage_Login extends StartBrowser {
	Properties prop;
	String excelfile = "C:\\Users\\Dell\\Downloads\\pAInITe_2024-main\\ExcelTestInputData\\TestData.xlsx";
	String excelsheet = "Login";
	String exsheet2 = "NegativeLogin";

	//@JiraPolicy(logTicketReady=true)
		//@Test(priority = 0, enabled = true)
		@Test(priority=0)
		//@Parameters("browser")
		public void LoginTestCase() throws Throwable {
			//ScreenRecorderUtil.startRecord("LoginTestCase");
		//	Log.startTestCase("Positive Login Test Case-----");
		//	Log.info("--Test Scenario login with correct credentials---");
			CommonFuns hm1 = new CommonFuns();

			String Email = XLUtils.getStringCellData(excelfile, excelsheet, 1, 0); 
			String Pwd = XLUtils.getStringCellData(excelfile, excelsheet, 1, 1);

			hm1.logIn(Email, Pwd);
			Thread.sleep(5000);
			Log.endTestCase("---End of Positive Login Test Case--");
			//ScreenRecorderUtil.stopRecord();
		}

//		@JiraPolicy(logTicketReady=false)
//		@Test(priority = 1 ,description="Negative Login Test")
	//	
//		public void NegativeLoginTest() throws Throwable {
//			ScreenRecorderUtil.startRecord("NegativeLoginTest");
//			CommonFuns hm2 = new CommonFuns();
	//
//			int rowcount = XLUtils.getRowCount(excelfile, exsheet2);
//			for (int i = 1; i < rowcount; i++) {
//				String Email = XLUtils.getStringCellData(excelfile, exsheet2, i, 0);
//				String Pwd = XLUtils.getStringCellData(excelfile, exsheet2, i, 1);
	//
//				hm2.invalidLogIn(Email, Pwd);
//				Thread.sleep(5000);
//				ScreenRecorderUtil.stopRecord();
//			}
	//
//		}
	}

	