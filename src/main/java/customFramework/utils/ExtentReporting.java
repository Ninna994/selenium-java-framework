package customFramework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporting {
    static ExtentReports extent;

    public static ExtentReports getReportObject() {
        String path = System.getProperty("user.dir") + "\\reports\\index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);

        reporter.config().setReportName("Custom Framework Report Name");
        reporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();

        extent.attachReporter(reporter);
        extent.setSystemInfo("Framework creator", "Nikolina Djekic");
        extent.setSystemInfo("Linkedin", "https://www.linkedin.com/in/nikolina-djekic/");
        return extent;
    }

}