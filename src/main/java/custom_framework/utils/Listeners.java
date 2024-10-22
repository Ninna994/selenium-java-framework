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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Properties;

public class Listeners extends SharedMethods implements ITestListener {

    ExtentTest test;
    ExtentReports extent = ExtentReporting.getReportObject();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getTestClass().getName() + " - " + result.getMethod().getMethodName();
        test = extent.createTest(testName);
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
    public void onStart(ITestContext context) {
        if (shouldClearAllureResults()) {
            clearAllureResultsDirectory();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        extentTest.remove();
    }

    private boolean shouldClearAllureResults() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/settings.properties");
            properties.load(fileInputStream);
            return Boolean.parseBoolean(properties.getProperty("clear.allure.results"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Default to not clearing if the property cannot be read
    }

    private void clearAllureResultsDirectory() {
        Path allureResultsPath = Paths.get("target/allure-results");
        if (Files.exists(allureResultsPath)) {
            try (var paths = Files.walk(allureResultsPath)) {
                paths.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                log.warn("Failed to delete file: {}", path.toAbsolutePath(), e);
                            }
                        });
            } catch (IOException e) {
                log.error("Failed to clear allure results directory", e);
            }
        }
    }

}