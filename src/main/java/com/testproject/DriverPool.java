package com.testproject;

import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DriverPool extends ThreadLocal<RemoteWebDriver>{

    private static AtomicInteger maxThreadCount = new AtomicInteger();
    private static AtomicInteger poolSize = new AtomicInteger();
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unPaused = pauseLock.newCondition();

    public DriverPool(int size) {
        maxThreadCount.set(size);
    }

    public void init() {
        pauseLock.lock();
        try {
            while (maxThreadCount.get() <= poolSize.get()) {
                unPaused.await();
            }
            set(getDriver());
            poolSize.getAndIncrement();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pauseLock.unlock();
        }
    }

    private RemoteWebDriver getDriver() {
        DesiredCapabilities dc;
        RemoteWebDriver driver;
        String browser = Config.getProperty(Config.BROWSER);
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", getClass().getResource("/drivers/chromedriver.exe").getPath());
                dc = DesiredCapabilities.chrome();
                dc.setPlatform(Platform.ANY);
                dc.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                dc.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
                driver = new ChromeDriver();
                break;
            case "firefox":
                //init new FirefoxDriver
            case "ie":
                //init IE driver
            default:
                throw new IllegalArgumentException("Browser is not supported:" + browser);
        }
        return driver;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1 );
        prefs.put("download.prompt_for_download", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--start-maximized");
        return options;
    }


    @Override
    public void remove() {
        pauseLock.lock();
        try {
            super.remove();
            poolSize.decrementAndGet();
            unPaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

}
