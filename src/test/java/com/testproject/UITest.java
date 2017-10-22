package com.testproject;

import com.relevantcodes.extentreports.LogStatus;
import com.testproject.base.BaseTest;
import com.testproject.model.Amortization;
import com.testproject.model.PaymentFrequency;
import com.testproject.model.PurchasePrice;
import com.testproject.page.IndividualsPage;
import com.testproject.page.MortgagePaymentCalculatorPage;
import com.testproject.page.MortgagesPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UITest extends BaseTest {

    @Test
    public void seleniumTest() {
        logger = extent.startTest("seleniumTest");
        logger.log(LogStatus.INFO, String.format("Browser %s is launched", Config.getProperty(Config.BROWSER)));
        logger.log(LogStatus.INFO, String.format("Navigated to %s", Config.getProperty(Config.URL)));
        IndividualsPage individualsPage = new IndividualsPage();
        individualsPage.clickLoans();
        logger.log(LogStatus.INFO, "Click Loans link");
        MortgagesPage mortgagesPage = individualsPage.clickMortgage();
        logger.log(LogStatus.INFO, "Click Mortgage link");
        MortgagePaymentCalculatorPage calculatorPage = mortgagesPage.clickCalculatePayments();
        logger.log(LogStatus.INFO, "Click Calculate Payments button");
        logger.log(LogStatus.INFO, "Validating Purchase Price slider movement");
        long initialPrice = Long.parseLong(calculatorPage.getPurchasePriceValue());
        int initialXAxis = calculatorPage.getPurchasePriceSliderCoordinates().getX();
        calculatorPage.increasePurchasePrice();
        long priceAfterMovement = Long.parseLong(calculatorPage.getPurchasePriceValue());
        int xAxisAfterMovement = calculatorPage.getPurchasePriceSliderCoordinates().getX();
        logger.log(LogStatus.INFO, String.format("X-axis value of slider handle before movement: %d, after movement: %d", initialXAxis, xAxisAfterMovement));
        logger.log(LogStatus.INFO, String.format("Price in input field before movement: %d, after movement: %d", initialPrice, priceAfterMovement));
        assertTrue(xAxisAfterMovement > initialXAxis, "Purchase price slider was not moved on Plus button click");
        assertTrue(priceAfterMovement > initialPrice, "Price was not updated after Purchase Price slider movement");
        logger.log(LogStatus.PASS, "Purchase Price slider movement validated");

        calculatorPage.setPurchasePriceViaSliderButtons(PurchasePrice.VALUE_500K);
        logger.log(LogStatus.INFO, "Purchase Price is set to 500 000 via slider buttons");
        calculatorPage.setDownPaymentViaInput("50000");
        logger.log(LogStatus.INFO, "Down Payment is set to 50 000");
        calculatorPage.chooseAmortization(Amortization.YEARS_15);
        logger.log(LogStatus.INFO, "Amortization is set to 15 years");
        calculatorPage.chooseFrequency(PaymentFrequency.WEEKLY);
        logger.log(LogStatus.INFO, "Payment Frequency is set to WEEKLY");
        calculatorPage.setInterestRate(5);
        logger.log(LogStatus.INFO, "Interest rate is set to 5%");
        calculatorPage.clickCalculatePrice();
        logger.log(LogStatus.INFO, "Click Calculate Price button");
        assertEquals(calculatorPage.getPaymentResult(), "$ 836.75", "Incorrect price is shown!");
    }

}
