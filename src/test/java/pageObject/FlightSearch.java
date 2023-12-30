package pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import testCases.BaseClass;

public class FlightSearch extends BaseClass {

	WebDriver driver;

	public FlightSearch(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='i-b tipsy fare-summary-tooltip fs-18']")
	List<WebElement> priceList;

	@FindBy(xpath = "//div[@class='tuple']")
	List<WebElement> searchFlightsList;
	
	@FindBy (xpath = "//section[@class='srp  wr-hr-center wr-width grid pt-25']")
	WebElement searchSection;

	public FlightReview cheapestFlight() {
		Double minPrice = Double.MAX_VALUE;
		WebElement selectedFlight = null;
		for (int i = 0; i < searchFlightsList.size(); i++) {
			String priceText = searchFlightsList.get(i)
					.findElement(By.xpath("(//div[@class='i-b tipsy fare-summary-tooltip fs-18'])[" + (i + 1) + "]"))
					.getText().replaceAll("[^\\d.]", "");
			System.out.println(priceText);
			Double price = Double.parseDouble(priceText);
			if (price < minPrice) {
				minPrice = price;
				selectedFlight = searchFlightsList.get(i).findElement(By.xpath("(//div[@class='pr tipsy mb-8 book-button i-b ml-5 v-aligm-m'])["+(i+1)+"]"));
			}
		}
		System.out.println(minPrice);
		if (selectedFlight.getText().equalsIgnoreCase("Book Now")) {
		selectedFlight.click();
		} else {
			selectedFlight.click();
			selectedFlight = selectedFlight.findElement(By.xpath("(//div[@class='book-button i-b ml-5 pr'])[1]"));
			waitElementToAppear(selectedFlight, driver);
			selectedFlight.click();
		}
		return new FlightReview(driver);
	}
	
	public boolean validateFlightSearchPage() {
		waitElementToAppear(searchSection, driver);
		boolean status = searchSection.isDisplayed();
		return status;
	}

}
