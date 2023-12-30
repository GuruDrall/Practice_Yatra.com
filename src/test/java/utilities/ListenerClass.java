package utilities;

import java.io.File;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ListenerClass extends ReportTestNG implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("=============== Test Case \'" + result.getName() + "\' Started ==============");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test = report.createTest(result.getName());
		test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " --> Passed", ExtentColor.GREEN));
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test = report.createTest(result.getName());
		test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " --> Failed", ExtentColor.RED));
		
		String path = Screenshot(driver, result);
		File ss = new File(path);
		if (ss.exists()) {
			test.fail("Captured Screenshot for : " + result.getName() + " " + test.addScreenCaptureFromPath(path));
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test = report.createTest(result.getName());
		test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " --> Skipped", ExtentColor.YELLOW));
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		test = report.createTest(result.getName());
		test.log(Status.FAIL,
				MarkupHelper.createLabel(result.getName() + " --> Failed due to TimeOut", ExtentColor.AMBER));
	}

	@Override
	public void onStart(ITestContext context) {
		reportSetup();
		setReportEnvironment();
		System.out.println("=============== Setting Report Envionment ==============");
		Date date = context.getStartDate();
		System.out.println(date);
	}

	@Override
	public void onFinish(ITestContext context) {
		if (context.getFailedTests().size() == 0) {
			System.out.println("=============== All Test Cases Executed Successfully ===============");
		} else {
			System.out.println("====== Total Test Case Failed : " + context.getFailedTests().size() + " ======");
		}
		report.flush();
	}

}
