package pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import testCases.BaseClass;

public class FlightHome extends BaseClass {
	WebDriver driver;

	public FlightHome(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "#booking_engine_flights")
	WebElement flightIcon;

	@FindBy(css = "#booking_engine_hotels")
	WebElement hotelIcon;

	@FindBy(xpath = "//a[@title='One Way']")
	WebElement oneWayBtn;

	@FindBy(css = "#BE_flight_origin_city")
	WebElement departFrom;

	@FindBy(css = ".ac_airport")
	List<WebElement> departDropDown;

	@FindBy(xpath = "//label[@for='BE_flight_arrival_city']")
	WebElement goingTo;

	@FindBy(css = ".BE_flight_origin_date")
	WebElement departureDate;

	@FindBy(xpath = "//div[@class='js-month-container']//div[@class='month-title']")
	List<WebElement> calenderMonth;

	@FindBy(xpath = "//div[@class='js-month-container']//div[@class='day-container']")
	List<WebElement> calenderDay;

	@FindBy(xpath = "//div[@class='js-month-container']//div[@class='day-container']/table/tbody//tr//td[not(@class='inActiveTD weekend' or @class='inActiveTD')]")
	List<WebElement> calenderDates;

	@FindBy(css = ".txt-ellipses")
	WebElement travelClass;

	@FindBy(xpath = "//li[@class='enabled _msddli_' or @class='enabled _msddli_ selected']/span[text()='Economy']")
	WebElement economyBtn;

	@FindBy(xpath = "//li[@class='enabled _msddli_'  or @class='enabled _msddli_ selected']/span[text()='Business']")
	WebElement businessBtn;

	@FindBy(xpath = "//a[@title='Non Stop Flights']")
	WebElement NonStopFlightBtn;

	@FindBy(xpath = "//div[@data-flightagegroup='adult']/div[@data-flightagegroup='adult']//div/span[@class='ddSpinnerPlus']")
	WebElement adultPlusBtn;

	@FindBy(xpath = "//div[@data-flightagegroup='adult']/div[@data-flightagegroup='child']//div/span[@class='ddSpinnerPlus']")
	WebElement childPlusBtn;

	@FindBy(xpath = "//div[@data-flightagegroup='adult']/div[@data-flightagegroup='infant']//div/span[@class='ddSpinnerPlus']")
	WebElement infantPlusBtn;

	@FindBy(xpath = "//input[@value='Search Flights']")
	WebElement searchFlightsBtn;
	
	int adultCount = 0;
	int childCount = 0;
	int infantCount = 0;
	

	public boolean validateFlightHomePage() {
		String color = flightIcon.getCssValue("Color");
//		System.out.println(color);
		if (color.equals("rgb(234, 35, 48)")) {
			return true;
		}
		return false;
	}

	public void clickHotelIcon() {
		hotelIcon.click();
	}

	public void setDepartFrom(String departCity) throws InterruptedException {
		departFrom.click();
		WebElement selectedCity = null;
		for (int i = 0; i < departDropDown.size(); i++) {
			String cityName = departDropDown.get(i).getText();
			System.out.println(cityName);
			if (cityName.contains(departCity)) {
				selectedCity = departDropDown.get(i);
				System.out.println("============DepartFromSelected===================");
				break;
			}

		}
		selectedCity.click();
	}

	public void setGoingTo(String goingCity) throws InterruptedException {
		goingTo.click();
		WebElement selectedCity = null;
		for (int i = 0; i < departDropDown.size(); i++) {
			String cityName = departDropDown.get(i).getText();
			System.out.println(cityName);
			if (cityName.contains(goingCity)) {
				selectedCity = departDropDown.get(i);
				System.out.println("=============GoingToSelected==================");
				break;
			}
		}
		selectedCity.click();
	}

	public void clickDepartureDate() {
		departureDate.click();
	}

	public void selectDepartureDate(String dd, String mmmm, String yy) {
		WebElement selectedMonth = null;
		for (int i = 0; i < calenderMonth.size(); i++) {
			String monthYear = calenderMonth.get(i).getText(); // January' 24
			String monthYears[] = monthYear.split("'");
			String month = monthYears[0].trim();
			String year = monthYears[1].trim();
			if (month.equals(mmmm) && year.equals(yy)) {
//				System.out.println("matched --> " + i);
				selectedMonth = calenderMonth.get(i);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectedMonth);
//				System.out.println(i + ": is i");
				List<WebElement> listOfDates = calenderMonth.get(i).findElements(By.xpath("(//table)[" + (i + 3)
						+ "]/tbody//tr//td[not(@class='inActiveTD weekend' or @class='inActiveTD')]"));
				for (int j = 0; j < listOfDates.size(); j++) {
					String datePrice = listOfDates.get(j).getText();
					String[] parts = datePrice.split("\n");
					// Assuming the date is always the first part
					String date = parts[0].trim();
					if (date.equals(dd)) {
						listOfDates.get(j).click();
						break;
					}
				}
			}
		}
	}

	public void selectTravelClass() throws InterruptedException {
		travelClass.click();
	}

	public void selectBusinessClass() {
		businessBtn.click();
	}

	public void selectEconomyClass() {
//		waitElementToAppear(economyBtn, driver);
		economyBtn.click();
	}

	public boolean confirmEconomyClassSelected() {
//		waitElementToAppear(economyBtn, driver);
		String color = economyBtn.getCssValue("Color");
//		System.out.println(color);
		if (color.equals("rgb(0, 0, 0)")) {
			return true;
		} else if (color.equals("rgb(102, 102, 102)")) {
			System.out.println("Economy Class is not selected");
		}
		return false;

	}

	public boolean confirmBusinessClassSelected() {
//		waitElementToAppear(businessBtn, driver);
		String color = businessBtn.getCssValue("Color");
//		System.out.println(color);
		if (color.equals("rgb(0, 0, 0)")) {
			return true;
		} else if (color.equals("rgb(102, 102, 102)")) {
			System.out.println("Business Class is not selected");
		}
		return false;
	}

	public void addTravellers(int adult, int child, int infant) {
		adultCount = adult;
		childCount = child;
		infantCount = infant;
		if (adult != 0) {
			for (int i = 1; i < adult; i++) {
				adultPlusBtn.click();
			}
		}
		if (child != 0) {
			for (int i = 1; i <= child; i++) {
				childPlusBtn.click();
			}
		}
		if (infant != 0) {
			for (int i = 1; i <= infant; i++) {
				infantPlusBtn.click();
			}
		}

	}
	
	public void selectNonStopFlight() {
		NonStopFlightBtn.click();
	}
	
	public FlightSearch clickSearchFlights() {
		searchFlightsBtn.click();
		return new FlightSearch(driver);
	}
	
	public int getAdultCount() {
		return adultCount;
	}
	
	public int getChildCount() {
		return childCount;
	}

	public int getInfantCount() {
		return infantCount;
	}
}
