package com.testproject.page;

import com.testproject.Driver;
import com.testproject.FieldDecorator;
import com.testproject.control.WebControl;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public abstract class BasePage {

    protected WebDriver driver = Driver.getDriver();

    protected BasePage() {
        PageFactory.initElements(new FieldDecorator(new DefaultElementLocatorFactory(driver)), this);
    }

    protected void scrollToElement(WebControl webControl) {
        scrollToElement(webControl.getWebElement());
    }

    protected void scrollToElement(WebElement webElement) {
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).build().perform();
        getWait(5).until(ExpectedConditions.visibilityOf(webElement));
    }

    protected FluentWait<WebDriver> getWait(long seconds) {
        return new WebDriverWait(driver, seconds).pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(StaleElementReferenceException.class);
    }

    protected boolean waitForControl(WebControl webControl) {
        return getWait(240).until(webDriver -> webControl != null && webControl.isDisplayed());
    }

    protected abstract boolean exist();

}
