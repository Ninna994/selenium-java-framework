package customFramework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.log.Log;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class FrameworkSetup {
    /*
     * ****************************** ATTENTION ******************************
     * ****************************** DO NOT CHANGE ANYTHING IN THIS FILE ******************************
     */

    //--------------------- Framework Setup Information ---------------------//

    public static Logger log = LogManager.getLogger();
    public long timeout = 10;
    public ExtendedBy By = new ExtendedBy();
    protected Properties settings = Settings.load("settings.properties");
    public String browser = settings.getProperty("browser");
    public String environment = settings.getProperty("environment");
    public String deviceName = settings.getProperty("device");
    public String apkName = settings.getProperty("apkName");
    public Map<String, String> values = null;

    //--------------------- Files Setup Information ---------------------//

    public String fileSeparator = System.getProperty("file.separator");
    public String fileDirectory = System.getProperty("user.dir") + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "testData" + fileSeparator;
    public String screenshotDestinationReporting = System.getProperty("user.dir") + fileSeparator + "reports" + fileSeparator;

    //--------------------- Project Setup Information ---------------------//

    public String projectUrl = getValues().get("projectUrl");
    public String projectEmail = getValues().get("projectEmail");
    public String projectPassword = getValues().get("projectPassword");

    //--------------------- Selenium Driver Setup ---------------------//

    @BeforeMethod(alwaysRun = true)
    public void startDriver() {
        if (settings.getProperty("run").equalsIgnoreCase("local")) {
            logSeparator();
            startLocalDriver();
        } else if (settings.getProperty("run").equalsIgnoreCase("remote")) {
            // startRemoteDriver(); - TODO
        }
    }

    @AfterMethod(alwaysRun = true)
    public void stopDriver() {
            driver().quit();
    }

    public WebDriver driver() {
        return DriverThreadLocal.getDriver();
    }

    /**
     * This method pulls the variable values based on which environment has been set in the settings.properties file.
     *
     * @return the environment-specific variables.
     */
    public Map<String, String> getValues() {
        if (values != null) {
            return values;
        }
        values = new HashMap<>();
        if ("PROD".equalsIgnoreCase(environment)) {
            values.put("projectUrl", "https://example.com");
            values.put("projectEmail", settings.getProperty("projectProdEmail"));
            values.put("projectPassword", settings.getProperty("projectProdPassword"));
        }
        if ("TEST".equalsIgnoreCase(environment)) {
            values.put("projectUrl", "https://example.com");
            values.put("projectEmail", settings.getProperty("projectTestEmail"));
            values.put("projectPassword", settings.getProperty("projectTestPassword"));
        }
        return values;
    }

    //--------------------- Driver Configuration ---------------------//

    private void startLocalDriver() {
        WebDriver driver = driver();
        if (browser.equalsIgnoreCase("Chrome")) {
            Map<String, Object> chromePrefs = new HashMap<>();

            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", fileDirectory);
            chromePrefs.put("selectfile.last_directory", fileDirectory);
            chromePrefs.put("download.prompt_for_download", false);
            chromePrefs.put("directory_upgrade", true);

            ChromeOptions chromeOptions = new ChromeOptions();

            chromeOptions.setExperimentalOption("prefs", chromePrefs);
            chromeOptions.addArguments("--incognito", "--remote-allow-origins=*", "ignore-certificate-errors");
            driver = new ChromeDriver(chromeOptions);

            DevTools devTools = ((ChromeDriver) driver).getDevTools();
            devTools.createSession();
            devTools.send(Log.enable());
            devTools.addListener(Log.entryAdded(), entryAdded -> {
                String logText = entryAdded.getText();
                String logLevel = entryAdded.getLevel().toString();

                if (logLevel.equals("warning") || logLevel.equals("error")) {
                    String consoleLogMessage = ("CONSOLE LOGGED " + logLevel + " ==> \n" + logText + "\n");
                    log.error(consoleLogMessage);
                }
            });
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        } else if (browser.equalsIgnoreCase("HeadlessChrome")) {
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

            Map<String, Object> chromePrefs = new HashMap<>();

            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", fileDirectory);
            chromePrefs.put("selectfile.last_directory", fileDirectory);
            chromePrefs.put("download.prompt_for_download", false);
            chromePrefs.put("directory_upgrade", true);

            ChromeOptions chromeOptions = new ChromeOptions();

            chromeOptions.setExperimentalOption("prefs", chromePrefs);
            chromeOptions.addArguments("--incognito", "--headless", "--disable-extensions", "--window-size=1920,1080");

            driver = new ChromeDriver(chromeOptions);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        } else if (browser.equalsIgnoreCase("MobileChrome")) {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "Nexus 5");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            chromeOptions.addArguments("--incognito", "--remote-allow-origins=*", "ignore-certificate-errors");
            driver = new ChromeDriver(chromeOptions);
        }
        DriverThreadLocal.setDriver(driver);
    }


    private static void logSeparator() {
        log.info("Test started");
    }

}