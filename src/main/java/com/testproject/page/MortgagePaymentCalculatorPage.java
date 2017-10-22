package com.testproject.page;

import com.testproject.control.Button;
import com.testproject.control.Input;
import com.testproject.model.Amortization;
import com.testproject.model.PaymentFrequency;
import com.testproject.model.PurchasePrice;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MortgagePaymentCalculatorPage extends BasePage {

    @FindBy(id = "PrixPropriete")
    private Input purchasePrice;

    @FindBy(id = "PrixProprietePlus")
    private Button increasePurchasePrice;

    @FindBy(id = "PrixProprieteMinus")
    private Button decreasePurchasePrice;

    @FindBy(xpath = "//div[contains(@class,'slider-handle')]")
    private WebElement slider;

    @FindBy(id = "MiseDeFond")
    private Input downPayment;

    @FindBy(id = "TauxInteret")
    private Input interestRate;

    @FindBy(id = "btn_calculer")
    private Button calculatePriceBtn;

    @FindBy(id = "paiement-resultats")
    private WebElement paymentResult;

    @FindBy(xpath = "(//div[@class='selectric'])[1]/b")
    private Button amortizationDropdownBtn;

    @FindBy(xpath = "(//div[@class='selectric-scroll'])[1]/ul/li")
    private List<WebElement> amortizationOptions;

    @FindBy(xpath = "(//div[@class='selectric'])[2]/b")
    private Button frequencyDropdownBtn;

    @FindBy(xpath = "(//div[@class='selectric-scroll'])[2]/ul/li")
    private List<WebElement> frequencyOptions;

    public Point getPurchasePriceSliderCoordinates() {
        return slider.getLocation();
    }

    public String getPurchasePriceValue() {
        scrollToElement(purchasePrice);
        return purchasePrice.getValue();
    }

    public void setPurchasePriceViaInput(String text) {
        scrollToElement(purchasePrice);
        purchasePrice.sendText(text);
    }

    public void setDownPaymentViaInput(String text) {
        scrollToElement(downPayment);
        downPayment.sendText(text);
    }

    public void increasePurchasePrice() {
        scrollToElement(increasePurchasePrice);
        increasePurchasePrice.click();
    }

    public void decreasePurchasePrice() {
        scrollToElement(decreasePurchasePrice);
        decreasePurchasePrice.click();
    }

    public void setPurchasePriceViaSliderButtons(PurchasePrice price) {
        scrollToElement(increasePurchasePrice);
        if (price.getNumericPrice() > Long.parseLong(purchasePrice.getValue())) {
            do {
                increasePurchasePrice();
            } while (!price.getPrice().equals(purchasePrice.getValue()));
        } else if (price.getNumericPrice() < Long.parseLong(purchasePrice.getValue())) {
            do {
                decreasePurchasePrice();
            } while (!price.getPrice().equals(purchasePrice.getValue()));
        }
    }

    public void chooseAmortization(Amortization value) {
        scrollToElement(amortizationDropdownBtn);
        amortizationDropdownBtn.click();
        amortizationOptions.get(value.getElementIndex()).click();
    }

    public void chooseFrequency(PaymentFrequency frequency) {
        scrollToElement(frequencyDropdownBtn);
        frequencyDropdownBtn.click();
        frequencyOptions.get(frequency.getElementIndex()).click();
    }

    public void setInterestRate(int value) {
        scrollToElement(interestRate);
        interestRate.sendText(String.valueOf(value));
    }

    public void clickCalculatePrice() {
        calculatePriceBtn.click();
        getWait(60).until(driver -> paymentResult != null);
    }

    public String getPaymentResult() {
        scrollToElement(paymentResult);
        return paymentResult.getText();
    }

    @Override
    protected boolean exist() {
        return waitForControl(purchasePrice);
    }

}
