package com.Mohs10.TestScripts;

import org.testng.annotations.Test;
import com.Mohs10.Base.ScreenRecorderUtil;
import com.Mohs10.Base.StartBrowser;
import com.Mohs10.Functions.CommonFuns;
import com.Mohs10.utility.Log;

public class ValidateTitleAndLogo extends StartBrowser {	

	// Verify Logo
	@Test(groups = "Smoke")
	public void verifyLogo() throws Throwable {
		ScreenRecorderUtil.startRecord("Verify Logo");
		Log.startTestCase("verifyLogo");
		CommonFuns cnf = new CommonFuns();
		cnf.VerifyingLogo();
		ScreenRecorderUtil.stopRecord();
	}

	// Validate Title
	@Test(groups = "Smoke")
	public void Verify_Title() throws Exception {
		ScreenRecorderUtil.startRecord("Verify Title");
		Log.startTestCase("---------------------------------Verify Title Test Case---------------------------------");

		CommonFuns hm1 = new CommonFuns();
		hm1.VerifyingWebsiteTitle();

		Log.endTestCase(
				"---------------------------------End of Verify Title Test Case---------------------------------");
		ScreenRecorderUtil.stopRecord();
	}
}
