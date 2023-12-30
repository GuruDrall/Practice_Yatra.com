package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;

public class ReportTestNG extends BaseClass {
	ExtentSparkReporter reporter;
	ExtentReports report;
	ExtentTest test;
	
	
	public void reportSetup() {
		reporter = new ExtentSparkReporter("TestNG_Reports\\BookFlight.html");
		report = new ExtentReports();
		report.attachReporter(reporter);
	}
	
	public void setReportEnvironment() {
		report.setSystemInfo("Author", "Gurdeep");
		report.setSystemInfo("OS", "Windows");
		report.setSystemInfo("Window Version", "Windows 10");
		report.setSystemInfo("Browser", rc.getBrowser().toUpperCase());
		
		reporter.config().setDocumentTitle("Validate Flight Booking Function");
		reporter.config().setReportName("Yatra.com");
		reporter.config().setTheme(Theme.STANDARD);
	}

}
