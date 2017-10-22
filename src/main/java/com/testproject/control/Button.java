package com.testproject.control;

import org.openqa.selenium.WebElement;

public class Button extends WebControl {

    public Button(WebElement webElement) {
        super(webElement);
    }

    public void click() {
        webElement.click();
    }

}
