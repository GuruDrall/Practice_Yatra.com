package testCases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import pageObject.FlightHome;
import pageObject.FlightPayment;
import pageObject.FlightReview;
import pageObject.FlightSearch;
import utilities.ReadClass;

public class BaseClass {

	public ReadClass rc = new ReadClass();
	public static WebDriver driver;
	public WebDriverWait wait;
	FlightHome fh;
	FlightSearch fs;
	FlightReview fr;
	FlightPayment fp;
	Actions act;
	ChromeOptions chromeOptions = new ChromeOptions();
	FirefoxOptions firefoxOptions = new FirefoxOptions();
	InternetExplorerOptions ieOptions = new InternetExplorerOptions();
	EdgeOptions edgeOptions = new EdgeOptions();

	void launchBrowser() {
		String brow = rc.getBrowser();
		if (brow.equalsIgnoreCase("chrome")) {
			chromeOptions.addArguments("--disable-geolocation");
			chromeOptions.addArguments("--disable-cookies");
			driver = new ChromeDriver(chromeOptions);
		} else if (brow.equalsIgnoreCase("firefox")) {
			firefoxOptions.addPreference("geo.enabled", false);
			driver = new FirefoxDriver();

		} else if (brow.equalsIgnoreCase("ie")) {
			try {
				Runtime.getRuntime().exec(
						"reg add \"HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\Geolocation\\Service\" /v EnableGeolocation /t REG_DWORD /d 0 /f");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			driver = new InternetExplorerDriver(ieOptions);
		} else if (brow.equalsIgnoreCase("edge")) {
			edgeOptions.setCapability("useAutomationExtension", false);
			edgeOptions.setCapability("disable-geolocation", true);
			driver = new EdgeDriver(edgeOptions);
		} else {
			System.out.println("Check browser");
		}
		System.out.println(brow);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	}

	FlightHome getUrl() {
		driver.get(rc.getUrl());
		return new FlightHome(driver);
	}
	
	public void waitElementToAppear(WebElement ele, WebDriver driver) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
	public void clickElement(WebElement ele, WebDriver driver) {
		act = new Actions(driver);
		act.click(ele).perform();
	}
	
	public String Screenshot(WebDriver driver, ITestResult result){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy_HH-mm");
	    String timestamp = dateFormat.format(new Date());
		
		TakesScreenshot ss = (TakesScreenshot)driver;
		File temp = ss.getScreenshotAs(OutputType.FILE);
		String path = "C:\\Users\\gsdra\\eclipse-workspace\\YatraFlight\\Screenshots\\"+result.getName()+timestamp+"_ss.png";
		File dest = new File(path);
		try {
			FileUtils.copyFile(temp, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

}
