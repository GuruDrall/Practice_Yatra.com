package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import testCases.BaseClass;

public class FlightPayment extends BaseClass {
	
	WebDriver driver;

	public FlightPayment(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//h2[@class='grad']")
	WebElement paymentPage;
	
	@FindBy(xpath = "//span[@id='totalAmountSpann']")
	WebElement totalAmount;
	
	public void showTotalAmount() {
		System.out.println("Total Amount is : Rs." + totalAmount.getText());
	}
	
	public boolean validatePaymentPage(String title) {
		waitElementToAppear(paymentPage, driver);
		boolean status = paymentPage.getText().equalsIgnoreCase(title);
		return status;
	}

}
