package com.testproject.businesslayer;

import com.testproject.model.PurchasePrice;
import com.testproject.page.MortgagePaymentCalculatorPage;

public class MortgagePaymentCalculatorBO extends BaseBO {

    public void calculatePayment(PurchasePrice price) {
        MortgagePaymentCalculatorPage calculatorPage = new MortgagePaymentCalculatorPage();
        calculatorPage.setPurchasePriceViaSliderButtons(price);
    }

}
