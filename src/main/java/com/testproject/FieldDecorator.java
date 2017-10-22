package com.testproject;

import com.testproject.control.WebControl;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class FieldDecorator extends DefaultFieldDecorator {

    public FieldDecorator(ElementLocatorFactory factory) {
        super(factory);
    }

    public FieldDecorator(final SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        Class<?> clazz = field.getType();
        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }
        if (field.getAnnotation(FindBy.class) != null && WebControl.class.isAssignableFrom(field.getType())) {
            try {
                WebElement proxy = proxyForLocator(loader, locator);
                return clazz.getConstructor(WebElement.class).newInstance(proxy);
            } catch (Exception e) {
                System.out.println("WebElement can't be represented as " + clazz);
            }
        }
        return super.decorate(loader, field);
    }

}
