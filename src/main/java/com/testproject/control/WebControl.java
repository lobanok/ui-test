package com.testproject.control;

import com.testproject.Driver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebControl {

    protected WebDriver driver;
    protected WebElement webElement;

    public WebControl(WebElement webElement) {
        driver = Driver.getDriver();
        this.webElement = webElement;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    public void focus() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus()", webElement);
    }

    public void blur() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus()", webElement);
    }

}
