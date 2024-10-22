package custom_framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.Properties;

public class ExtentReporting {
    static ExtentReports extent;

    public static ExtentReports getReportObject() {
        String path = System.getProperty("user.dir") + "\\reports\\index.html";
        Properties settings = Settings.load("settings.properties");
        String browser = settings.getProperty("browser");
        String environment = settings.getProperty("environment");
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);

        reporter.config().setReportName("Custom Framework Report Name");
        reporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();

        extent.attachReporter(reporter);
        extent.setSystemInfo("Framework creator", "Nikolina Djekic");
        extent.setSystemInfo("Linkedin", "https://www.linkedin.com/in/nikolina-djekic/");
        extent.setSystemInfo("Browser", System.getProperty("browser", browser));
        extent.setSystemInfo("Environment", System.getProperty("environment", environment));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        return extent;
    }

}