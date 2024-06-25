package com.Mohs10.Reports;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.Mohs10.Base.StartBrowser;
import com.Mohs10.Base.XLUtils;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

public class New_PDF_Report extends StartBrowser {

	// Create a set of browser names
	Set<String> Browsernames = new HashSet<>();

	@Test
//*****************************************************Data Fetching Script*******************************************************//
	public void PDFreports(ITestContext context) throws IOException {
		List<String> failText = new ArrayList<>();
		List<String> failTime = new ArrayList<>();
		List<String> passText = new ArrayList<>();
		List<String> passTime = new ArrayList<>();
		List<String> skippedText = new ArrayList<>();
		// List<String> skippedTime = new ArrayList<>();

		String excelfile = "ExcelTestInputData\\PDF.xlsx";
		String excelsheet = "PDFVALUES";
		String ProjectN = XLUtils.getStringCellData(excelfile, excelsheet, 1, 3);
		String TesterN = XLUtils.getStringCellData(excelfile, excelsheet, 1, 2);
		String M10logo = "Logo\\M10logo.png";
		//String Screenshotspath = "Screenshots//";
        String Screenshotspath = XLUtils.getStringCellData(excelfile, excelsheet, 1, 1);
		// Loop through each HTML report and get its details
		for (int m = 1; m <= 3; m++) {
			String htmlpath = "test-output\\Suite\\TestNG_Report" + m + ".html";
			//String htmlpath = "test-output\\Sample\\TestNG_Report1" + m + ".html";
			//String htmlpath = "test-output\\Sample\\TestNG_Report" + m + ".html";
			//String htmlpath = "test-output\\Sample\\TestNG_Report1.html";
			File filee = new File(htmlpath);
			String HTMLPath = filee.getAbsolutePath();
			driver.get(HTMLPath); // opening .html file

			String TestPFS = driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]")).getText(); // TestCas
																											// status(P/F/S)
			// String[] arr = TestPFS.split("/");
			String Timestamp = driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[2]")).getText(); // Execution
																												// start
																												// time
			String TotalTimetaken = driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[2]")).getText();

//fetching passes testcases time and title
			List<WebElement> rows = driver
					.findElements(By.xpath("/html/body/p//table[@class=\"invocation-passed\"]/tbody/tr"));
			for (int i = 2; i < rows.size(); i++) {
				List<WebElement> cols = rows.get(i).findElements(By.tagName("td")); // Breaking xpath
				List<WebElement> cols1 = cols.get(0).findElements(By.tagName("b")); // getting O'th column value
				String title = cols1.get(0).getText();
				passText.add(title);
				String time = cols.get(2).getText(); // getting 1'th column value
				passTime.add(time);
			}

//fetching failed testcases time and title
			List<WebElement> rowsF = driver
					.findElements(By.xpath("/html/body/p//table[@class=\"invocation-failed\"]/tbody/tr"));
			for (int f = 2; f < rowsF.size(); f++) {
				List<WebElement> colsF = rowsF.get(f).findElements(By.tagName("td"));
				List<WebElement> cols1F = colsF.get(0).findElements(By.tagName("b"));
				String title = cols1F.get(0).getText();
				failText.add(title);
				String time = colsF.get(2).getText();
				failTime.add(time);
			}

//fetching skipped testcases time and title
			List<WebElement> rowsS = driver
					.findElements(By.xpath("/html/body/p//table[@class=\"invocation-skipped\"]/tbody/tr"));
			for (int g = 2; g < rowsS.size(); g++) {
				List<WebElement> colsS = rowsS.get(g).findElements(By.tagName("td"));
				List<WebElement> cols1S = colsS.get(0).findElements(By.tagName("b"));
				String title = cols1S.get(0).getText();
				skippedText.add(title);
			}

//************************************************************PDF Script*************************************************************//
// Creating a PdfWriter
			String dest = "Reports/ClientReport.pdf";
			PdfWriter writer = new PdfWriter(dest);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf); // creating document of PDF format

//Script to add logo image in pdf
			ImageData data1 = ImageDataFactory.create(M10logo);
			Image img = new Image(data1);
			img.scaleToFit(100f, 100f); // image size
			img.setFixedPosition(450, 800); // image position
			document.add(img);

// Table-0 (Automation Report)///
			float[] pointColumnWidths = { 260f, 260f }; // fixing table dimension(columns)
			Table table = new Table(pointColumnWidths).setBorder(new SolidBorder(Color.BLACK, 1))
					.setBackgroundColor(Color.LIGHT_GRAY); // Creating Table-0

			Text text = new Text("Report Summary"); // making title "automation report"
			text.setFont(PdfFontFactory.createFont(FontConstants.COURIER)).setBold().setFontSize(18)
					.setFontColor(Color.BLUE).setUnderline().setTextAlignment(TextAlignment.CENTER);

			Paragraph para = new Paragraph(text); // adding title to paragraph
			document.add(para); // adding paragraph to document

			table.addCell(new Cell().add("Project Name")); // making Table-0 cells
			table.addCell(new Cell().add(ProjectN));
			table.addCell(new Cell().add("Tester Name"));
			table.addCell(new Cell().add(TesterN));

			table.addCell(new Cell().add("Browser Name"));
			System.out.println(
					"***********************************************************************************************");

			// The sequence of browser name in xml file should be chrom, firefox and Edge
			if (browserName.equalsIgnoreCase("chrome")) {
				table.addCell(new Cell().add("chrome"));
			} else if (browserName.equalsIgnoreCase("firefox")) {
				table.addCell(new Cell().add("chrome,firefox"));
			} else if (browserName.equalsIgnoreCase("edge")) {
				table.addCell(new Cell().add("chrome,firefox,edge"));
			}

			System.out.println(
					"***********************************************************************************************");

			table.addCell(new Cell().add("Started on"));
			table.addCell(new Cell().add(Timestamp));

			table.addCell(new Cell().add("Total Tests run"));
			int TC = passText.size() + failText.size() + skippedText.size();

			String strNum = Integer.toString(TC);
			table.addCell(new Cell().add(strNum));
			table.addCell(new Cell().add("Test Passed/Failed/Skipped"));
			table.addCell(new Cell().add(TestPFS));
			table.addCell(new Cell().add("Total TimeTaken for Execution"));
			table.addCell(new Cell().add(TotalTimetaken));

			document.add(table); // Adding Table-0 cells after paragraph

// Table-1 (Passed TestCases Report)
			if (!passText.isEmpty()) {

				float[] pointColumnWidths1 = { 260f, 260f };
				Table table1 = new Table(pointColumnWidths1);
				Text text1 = new Text("Passed Tests");
				text1.setFont(PdfFontFactory.createFont(FontConstants.COURIER)).setBold().setFontSize(18)
						.setFontColor(Color.BLUE).setUnderline().setTextAlignment(TextAlignment.CENTER);
				Paragraph para1 = new Paragraph(text1);
				document.add(para1);
				table1.addCell(new Cell().setBold().add("Test Case Name"));
				table1.addCell(new Cell().setBold().add("Time(Seconds)"));

				for (int a = 0; a < passText.size(); a++) {
					table1.addCell(new Cell().add(passText.get(a)));
					table1.addCell(new Cell().add(passTime.get(a)));

				}

				document.add(table1);
			}

// Table-2 (Failed TestCases Report)
			if (!failText.isEmpty()) {

				float[] pointColumnWidths1 = { 260f, 260f };
				Table table2 = new Table(pointColumnWidths1);
				Text text2 = new Text("Failed Tests");
				text2.setFont(PdfFontFactory.createFont(FontConstants.COURIER)).setBold().setFontSize(18)
						.setFontColor(Color.BLUE).setUnderline().setTextAlignment(TextAlignment.CENTER);
				Paragraph para2 = new Paragraph(text2);
				document.add(para2);
				table2.addCell(new Cell().setBold().add("Test Case Name"));
				table2.addCell(new Cell().setBold().add("Time(Seconds)"));

				for (int b = 0; b < failText.size(); b++) {
					table2.addCell(new Cell().add(failText.get(b)));
					table2.addCell(new Cell().add(failTime.get(b)));

				}

				document.add(table2);
			}

// Table-3 (Skipped TestCases Report)
			if (!skippedText.isEmpty()) {

				float[] pointColumnWidths3 = { 260f, 260f };
				Table table3 = new Table(pointColumnWidths3);
				Text text3 = new Text("Skipped Tests");
				text3.setFont(PdfFontFactory.createFont(FontConstants.COURIER)).setBold().setFontSize(18)
						.setFontColor(Color.BLUE).setUnderline().setTextAlignment(TextAlignment.RIGHT);
				Paragraph para4 = new Paragraph(text3);
				document.add(para4);
				table3.addCell(new Cell().setBold().add("Test Case Name"));
				table3.addCell(new Cell().setBold().add("Status"));

				for (int C = 0; C < skippedText.size(); C++) {
					table3.addCell(new Cell().add(skippedText.get(C)));
					table3.addCell(new Cell().add("Skipped"));
				}

				document.add(table3);
			}

			// Adding screen shots to pdf
			File file = new File(Screenshotspath);
			String[] ls = file.list(); // adding images in array

			float[] pointColumnWidths4 = { 260f, 260f };
			Table table4 = new Table(pointColumnWidths4);
			Text text4 = new Text("Screenshots");
			text4.setFont(PdfFontFactory.createFont(FontConstants.COURIER)).setBold().setFontSize(18)
					.setFontColor(Color.BLUE).setUnderline().setTextAlignment(TextAlignment.RIGHT);
			Paragraph para5 = new Paragraph(text4);
			document.add(para5);

			table4.addCell(new Cell().setBold().add("Description"));
			table4.addCell(new Cell().setBold().add("SnapShots"));
			for (String stepname : ls) {
				String[] stepname1 = stepname.split("\\.");
				String stepname2 = stepname1[0];
				String imFile = Screenshotspath + stepname;
				ImageData data = ImageDataFactory.create(imFile);
				Image image = new Image(data);
				table4.addCell(new Cell().add(stepname2));
				table4.addCell(new Cell().add(image.scaleToFit(260, 260)));
			}
			document.add(table4);

			document.close(); // Closing PDF Document
		}
	}
}
