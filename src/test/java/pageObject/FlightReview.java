package pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import testCases.BaseClass;

public class FlightReview extends BaseClass {

	WebDriver driver;

	public FlightReview(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='pull-left i-b pt5 ng-binding']")
	WebElement reviewPage;

	@FindBy(css = "#additionalContactEmail")
	WebElement inputEmail;

	@FindBy(css = "#additionalContactMobile")
	WebElement inputContactNum;

	@FindBy(xpath = "//article[@ng-repeat='traveller in travellerDetails']")
	List<WebElement> travelDetailList;

	@FindBy(xpath = "//input[@name='fname0']")
	WebElement inputAdultFirstName1;

	@FindBy(xpath = "//input[@name='fname0']")
	WebElement inputAdultLastName1;

	@FindBy(css = ".ng-touched") // option[text()='Title'] //3 only
	WebElement adultTitle;

	@FindBy(xpath = "//label[@class='align-checkbox fs-base cursor-pointer gray']//i[@class='ytfi-checker']")
	WebElement infoOnWatsapp;

	@FindBy(css = "#Infant_1_dob")
	WebElement infantDob;

	@FindBy(css = ".pax-proceed-btn.cursor-pointer")
	WebElement continueBtn;

	@FindBy(xpath = "(//button[@class='button primary rounded' and text()='Confirm'])[1]")
	WebElement confirmBtn;

	@FindBy(xpath = "//button[@autom='proceedTopayment']")
	WebElement proceedToPayBtn;

	@FindBy(xpath = "//button[@ng-click='seatAutoselect()']")
	WebElement yesPleaseBtn;

	@FindBy(xpath = "//input[@ng-click='continueWithInsurance(false)']")
	WebElement continueWithoutInsurance;

	@FindBy(xpath = "//button[@ng-click='continueSameFlight()']")
	WebElement continueFareChangeAlert;

	public boolean validateReviewPage(String reviewTitle) {
		waitElementToAppear(reviewPage, driver);
		String title = reviewPage.getText();
		if (title.equalsIgnoreCase(reviewTitle)) {
			return true;
		}
		return false;
	}

	public void enterEmailAndContactNum(String email, String contactNum) {
		inputEmail.sendKeys(email);
		inputContactNum.sendKeys(contactNum);
	}

	public void enterAdultDetails(FlightHome fh, String[] names) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", infantDob);
		int count = fh.getAdultCount();
		if (count == names.length) {
			String title = null;
			String fname = null;
			String lname = null;
			String name[] = names;
			for (int i = 0; i < count; i++) {
				String[] fullName = name[i].toString().split(" ");
//			for (String n : fullName) {
//				System.out.println(n);
//			}
				title = fullName[0].trim();
				fname = fullName[1].trim();
				lname = fullName[2].trim();

				WebElement selectTitle = travelDetailList.get(i)
						.findElement(By.xpath("//select[@id='title" + i + "']"));
				Select sel = new Select(selectTitle);
				sel.selectByVisibleText(title);

				travelDetailList.get(i).findElement(By.xpath("//input[@name='fname" + i + "']")).sendKeys(fname);
				travelDetailList.get(i).findElement(By.xpath("//input[@name='lname" + i + "']")).sendKeys(lname);

			}
		}
	}

	public void enterChildDetails(FlightHome fh, String[] names) {
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", infantDob);
		int count = fh.getChildCount();
		if (count == names.length) {
			String title = null;
			String fname = null;
			String lname = null;
			String name[] = names;
			for (int i = 0; i < count; i++) {
				String[] fullName = name[i].toString().split(" ");
				i = i + fh.getAdultCount();
//				for (String n : fullName) {
//					System.out.println(n);
//				}
				title = fullName[0].trim();
				fname = fullName[1].trim();
				lname = fullName[2].trim();

				WebElement selectTitle = travelDetailList.get(i)
						.findElement(By.xpath("//select[@id='title" + i + "']"));
				Select sel = new Select(selectTitle);
				sel.selectByVisibleText(title);

				travelDetailList.get(i).findElement(By.xpath("//input[@name='fname" + i + "']")).sendKeys(fname);
				travelDetailList.get(i).findElement(By.xpath("//input[@name='lname" + i + "']")).sendKeys(lname);
				i = i - fh.getAdultCount();
			}
		}
	}

	public void enterInfantDetails(FlightHome fh, String names[], String dob) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", inputContactNum);
		int count = fh.getInfantCount();
		if (count == names.length) {
			String title = null;
			String fname = null;
			String lname = null;
			String name[] = names;
			for (int i = 0; i < count; i++) {
				String[] fullName = name[i].toString().split(" ");
				i = i + fh.getAdultCount() + fh.getChildCount();
//			for (String n : fullName) {
//				System.out.println(n);
//			}
				title = fullName[0].trim();
				fname = fullName[1].trim();
				lname = fullName[2].trim();

				WebElement selectTitle = travelDetailList.get(i)
						.findElement(By.xpath("//select[@id='title" + i + "']"));
				Select sel = new Select(selectTitle);
				sel.selectByVisibleText(title);

				travelDetailList.get(i).findElement(By.xpath("//input[@name='fname" + i + "']")).sendKeys(fname);
				travelDetailList.get(i).findElement(By.xpath("//input[@name='lname" + i + "']")).sendKeys(lname);
				infantDob.sendKeys(dob);
				i = i - fh.getAdultCount() - fh.getChildCount();
			}
		}
	}

	public void clickContinue() {
		if (continueFareChangeAlert.isDisplayed()) {
			clickContinueFareChangeAlert();
		}
		continueBtn.click();
	}

	public void checkinfoOnWatsapp() {
		infoOnWatsapp.click();
	}

	public void clickConfirmDetails() {
		waitElementToAppear(confirmBtn, driver);
		clickElement(confirmBtn, driver);
	}

	public FlightPayment clickProceedToPayment() {
		if (proceedToPayBtn.isDisplayed()) {
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", proceedToPayBtn);
				proceedToPayBtn.click();
			} catch (Exception e) {
				System.out.println("Proceed to Payment Btn not available");
				if (continueWithoutInsurance.isDisplayed()) {
					clickContinueWithoutInsurance();
					e.printStackTrace();
				} else if (continueFareChangeAlert.isDisplayed()) {
					clickContinueFareChangeAlert();
					e.printStackTrace();
				} else if (yesPleaseBtn.isDisplayed()) {
					clickYesPlease();
				}
			}
		}

		return new FlightPayment(driver);
	}

	public void clickYesPlease() {
		waitElementToAppear(yesPleaseBtn, driver);
		clickElement(yesPleaseBtn, driver);
		if (continueWithoutInsurance.isDisplayed()) {
			clickContinueWithoutInsurance();
//				e.printStackTrace();
		} else if (continueFareChangeAlert.isDisplayed()) {
			clickContinueFareChangeAlert();
//				e.printStackTrace();
		}

	}

	public void clickContinueWithoutInsurance() {
		if (continueWithoutInsurance.isDisplayed()) {
			clickElement(continueWithoutInsurance, driver);
		} else if (yesPleaseBtn.isDisplayed()) {
			clickYesPlease();
		}
	}

	public void clickContinueFareChangeAlert() {
		clickElement(continueFareChangeAlert, driver);
	}

}
