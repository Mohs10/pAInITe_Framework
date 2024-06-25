package com.Mohs10.utility;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.Mohs10.Base.Action;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
public class ListenerClass extends ExtentReport implements ITestListener {

	// public static ThreadLocal<String> browserThreadLocal = new ThreadLocal<>();
	WebDriver driver;
    
	@Override
	public void onTestStart(ITestResult result) {
		
		System.out.println("Test Started: " + result.getName());
		test = extent.createTest(result.getName());
	}

	@Override
    public void onTestSuccess(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
        	System.out.println("Test Passed: " + result.getName());
            try {
				String imgPath = Action.captureScreenshot(result.getName());
				// Log a pass status with the screenshot in the Extent Report
	            test.pass("Pass Test case is: " + result.getName(), MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL,
                    MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            test.log(Status.FAIL,
                    MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
            String imgPath = Action.captureScreenshot(result.getName());

            test.fail("ScreenShot is Attached", MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());
        }
    }

	@Override
	public void onTestSkipped(ITestResult result) {
		if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Skipped Test case because: " + result.getName());
			System.out.println("Test Skipped: " + result.getName());
		}
	}

	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
		System.out.println("success %");
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("Test Suite started -----" + context.getName() + "-----");
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Test Suite finished -----" + context.getName() + "-----");
		extent.flush();
	}
}
