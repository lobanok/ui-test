package com.testproject.page;

import com.testproject.control.Button;
import org.openqa.selenium.support.FindBy;

public class MortgagesPage extends BasePage {

    @FindBy(css = "a.btn-full[href='/mortgage-payment-calculator']")
    private Button calculatePaymentsBtn;

    public MortgagePaymentCalculatorPage clickCalculatePayments() {
        scrollToElement(calculatePaymentsBtn);
        calculatePaymentsBtn.click();
        return new MortgagePaymentCalculatorPage();
    }

    @Override
    protected boolean exist() {
        return waitForControl(calculatePaymentsBtn);
    }
}
