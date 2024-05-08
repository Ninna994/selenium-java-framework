package custom_framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.io.IOException;

public class Listeners extends SharedMethods implements ITestListener {

    ExtentTest test;
    ExtentReports extent = ExtentReporting.getReportObject();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test); // to support parallel execution
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
        String testMethodName = result.getMethod().getMethodName();
        try {
            String screenshotPath = getScreenshotPath(testMethodName);
            extentTest.get().addScreenCaptureFromPath(getScreenshotPath(testMethodName), testMethodName);
            Allure.addAttachment(testMethodName + " - Failure Screenshot", new FileInputStream(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test skipped");
        String testMethodName = result.getMethod().getMethodName();
        try {
            String screenshotPath = getScreenshotPath(testMethodName);
            extentTest.get().addScreenCaptureFromPath(getScreenshotPath(testMethodName), testMethodName);
            Allure.addAttachment(testMethodName + " - Skip Screenshot", new FileInputStream(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        extentTest.remove();
    }

}