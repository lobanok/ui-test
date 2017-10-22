package com.testproject;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Set;

public class Driver {

    private static DriverPool driverPool = new DriverPool(Config.getIntProperty(Config.THREAD_COUNT));

    public static void startDriver() {
        driverPool.init();
    }

    public static RemoteWebDriver getDriver() {
        return driverPool.get();
    }

    private Driver() {}

    public static void stopDriver() {
        if (driverPool.get() != null) {
            try {
                Set<String> windowHandles = driverPool.get().getWindowHandles();
                for (String handle : windowHandles) {
                    driverPool.get().switchTo().window(handle);
                    driverPool.get().close();
                    driverPool.get().quit();
                }
            } finally {
                driverPool.remove();
            }
        }
    }

}
