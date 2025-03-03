package com.general.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public abstract class Reporter  {

	private static ExtentReports extent;
	private static final ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>(); // testSutie
	private static final ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();// testCase
	private static final ThreadLocal<String> testName = new ThreadLocal<String>();

	private String fileName = "result.html";
	private String pattern = "dd-MMM-yyyy HH-mm-ss";

	public String testcaseName, testDescription, authors, category, dataFileName, dataFileType, excelFileName;
	public static String folderName = "";

	@BeforeSuite(alwaysRun = true)
	public synchronized void startReport() {
		String date = new SimpleDateFormat(pattern).format(new Date());
		folderName = "reports/" + date;

		File folder = new File("./" + folderName);
		if (!folder.exists()) {
			folder.mkdir();
		}
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("./" + folderName + "/" + fileName);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("Salesforce");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("Salesforce");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	}

	@BeforeClass(alwaysRun = true)
	public synchronized void startTestCase() {
		ExtentTest parent = extent.createTest(testcaseName, testDescription);
		parent.assignCategory(category);
		parent.assignAuthor(authors);
		parentTest.set(parent);
		testName.set(testcaseName);
	}

	public synchronized void setNode() {
		ExtentTest child = parentTest.get().createNode(getTestName());
		test.set(child);
	}

	public abstract long takeSnap();

	public void reportStep(String desc, String status, boolean bSnap) {
		synchronized (test) {
			// Start reporting the step and snapshot
			Media img = null;
			if (bSnap && !(status.equalsIgnoreCase("INFO") || status.equalsIgnoreCase("skipped"))) {
				long snapNumber = 100000L;
				snapNumber = takeSnap();
				try {
					img = MediaEntityBuilder
							.createScreenCaptureFromPath("./../../" + folderName + "/images/" + snapNumber + ".jpg")
							.build();
				} catch (Exception e) {
				}
			}
			if (status.equalsIgnoreCase("pass")) {
				test.get().pass(desc, img);
			} else if (status.equalsIgnoreCase("fail")) { // additional steps to manage alert pop-up
				test.get().fail(desc, img);
				throw new RuntimeException("See the reporter for details.");

			} else if (status.equalsIgnoreCase("warning")) {
				test.get().warning(desc, img);
			} else if (status.equalsIgnoreCase("skipped")) {
				test.get().skip("The test is skipped due to dependency failure");
			} else if (status.equalsIgnoreCase("INFO")) {
				test.get().info(desc);
			}

		}
	}

	public void reportStep(String desc, String status) {
		reportStep(desc, status, true);
	}

	@AfterSuite(alwaysRun = true)
	public synchronized void endResult() {
		extent.flush();
	}

	public String getTestName() {
		return testName.get();
	}

	public Status getTestStatus() {
		return parentTest.get().getModel().getStatus();
	}

}