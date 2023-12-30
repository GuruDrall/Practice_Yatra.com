package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.FlightReview;
import pageObject.FlightSearch;

public class FlightTestCases extends BaseClass {

	@Test
	public void searchFlights() throws InterruptedException {
// =========================== Step 1 ===========================

		launchBrowser(); // launching browser
		fh = getUrl(); // getting Yatra.com url
		boolean flag = fh.validateFlightHomePage();
		Assert.assertTrue(flag, "Not at Flight Home Page");
		fh.setDepartFrom("New Delhi"); // Setting Depart From city
		fh.setGoingTo("Goa"); // setting Going to city
		fh.clickDepartureDate();
		fh.selectDepartureDate("12", "March", "24"); // selecting date of travel
		fh.selectTravelClass(); // selecting how many person travelling
		fh.selectBusinessClass();
		flag = fh.confirmBusinessClassSelected();
		Assert.assertTrue(flag, "Business Class not Selected");
		fh.selectEconomyClass();
		flag = fh.confirmEconomyClassSelected();
		Assert.assertTrue(flag, "Economy Class not Selected");
		fh.addTravellers(2, 1, 1); // 2 Adult, 1 Child and 1 Infant
		fh.selectNonStopFlight(); // Selecting non stop flights
		fs = fh.clickSearchFlights();

// =========================== Step 2 ===========================
		Assert.assertTrue(fs.validateFlightSearchPage(), "Not at Search Flight Page"); // checking if search flights are
																						// displayed or not
		fr = fs.cheapestFlight(); // selecting cheapest flight

// =========================== Step 3 ===========================
		Assert.assertTrue(fr.validateReviewPage("Review Your Booking"), "Not at Flight Review Page");
		// providing details
		fr.enterEmailAndContactNum("abc@gmail.com", "9898989898");
		fr.enterAdultDetails(fh, new String[] { "Mr. Rahul Kumar", "Mrs. Simran Kaur" }); // 2 Adult
		fr.enterChildDetails(fh, new String[] { "Miss Pooja Kumar" }); // 1 Child
		fr.enterInfantDetails(fh, new String[] { "Master Ram Kumar" }, "12092022"); // 1 Infant
		fr.clickContinue(); // Clicking continue after filling all details
		fr.clickConfirmDetails(); // Confirming details
		fp = fr.clickProceedToPayment(); // Proceeding to Pay

// =========================== Step 4 ===========================
		fr.clickYesPlease(); // Selecting default seats
		Assert.assertTrue(fp.validatePaymentPage("Payment Method"), "Not on Payment Page");
		fp.showTotalAmount(); // Showing total amount to be paid
		driver.close();
	}

}
