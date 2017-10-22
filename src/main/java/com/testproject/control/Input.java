package com.testproject.control;

import org.openqa.selenium.WebElement;

public class Input extends WebControl {

    public Input(WebElement webElement) {
        super(webElement);
    }

    public void sendText(String text) {
        focus();
        webElement.clear();
        if (text != null && !text.isEmpty()) {
            webElement.sendKeys(text);
        }
        blur();
    }

    public String getValue() {
        return webElement.getAttribute("value");
    }

}
