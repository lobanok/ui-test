package com.testproject.base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.testproject.Config;
import com.testproject.Driver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("squid:S2187")
public class BaseTest {

    private static final String REPORT_FILE = "testreport.html";

    protected static ExtentReports extent = new ExtentReports(REPORT_FILE, true);
    protected ExtentTest logger;
    private RemoteWebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestResult result) {
        logger = extent.startTest(result.getName());
        Driver.startDriver();
        driver = Driver.getDriver();
        logger.log(LogStatus.INFO, String.format("Browser %s is launched", Config.getProperty(Config.BROWSER)));
        driver.get(Config.getProperty(Config.URL));
        try {
            driver.manage().window().maximize();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
        logger.log(LogStatus.INFO, String.format("Navigated to %s", Config.getProperty(Config.URL)));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(LogStatus.FAIL, "Test failed: " + result.getName());
            logger.log(LogStatus.FAIL, "Test failed: " + result.getThrowable().getMessage());
            logger.log(LogStatus.FAIL, logger.addScreenCapture(getScreenshot(result.getName())));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(LogStatus.PASS, "Test passed: " + result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(LogStatus.SKIP, "Test skipped: " + result.getName());
        }
        extent.endTest(logger);
        Driver.stopDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void closeExtentReports() {
        extent.flush();
    }

    private String getScreenshot(String name) {
        String screenshotPath = String.format("./screenshots/%s.jpg", name);
        try {
            File screenshot = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }

}
