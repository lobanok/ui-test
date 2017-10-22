package com.testproject.page;

import com.testproject.control.Button;
import org.openqa.selenium.support.FindBy;

public class IndividualsPage extends BasePage {

    @FindBy(css = "a[href='/individuals/pret']")
    private Button loans;

    @FindBy(css = "a[href='/mortgage']")
    private Button mortgage;

    public void clickLoans() {
        loans.click();
    }

    public MortgagesPage clickMortgage() {
        getWait(30).until(driver -> mortgage.isDisplayed());
        mortgage.click();
        return new MortgagesPage();
    }

    @Override
    protected boolean exist() {
        return waitForControl(loans);
    }
}
