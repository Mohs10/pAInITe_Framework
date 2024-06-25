package com.Mohs10.TestScripts;

import org.testng.annotations.Test;

import com.Mohs10.Base.StartBrowser;
import com.Mohs10.Base.XLUtils;
import com.Mohs10.Functions.MamatvaProd_Fun;

public class MamatvaProd_Scripts extends StartBrowser {

	
	String excelFilePath = "C:\\Users\\Dell\\Downloads\\pAInITe_2024-main\\ExcelTestInputData\\TestData.xlsx";
	String excelsheet = "MamatvaProd";
	
	
	

	  @Test(priority = 0)
	    public void Mamatva() throws Exception 
	    {
		  MamatvaProd_Fun hm1 = new MamatvaProd_Fun();
		 String Email = XLUtils.getStringCellData(excelFilePath, excelsheet, 1, 0);
		  
          String Password = XLUtils.getStringCellData(excelFilePath, excelsheet, 1, 1);
	        hm1.Login(Email, Password);
	          
	            Thread.sleep(7000);
	    }
}
