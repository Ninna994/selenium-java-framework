package custom_framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v124.log.Log;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class FrameworkSetup {
    private final Properties settings = Settings.load("settings.properties");
    private final String browser = settings.getProperty("browser");
    private final String environment = settings.getProperty("environment");
    private final String system = settings.getProperty("system");
    private final Map<String, String> systemProperties = new HashMap<>();

    public final ExtendedBy By = new ExtendedBy();
    public static final Logger log = LogManager.getLogger();
    public final long timeout = Long.parseLong(settings.getProperty("timeout"));

    public final String fileSeparator = System.getProperty("file.separator");
    public final String fileDirectory = System.getProperty("user.dir") + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "testData" + fileSeparator;
    public final String screenshotDestinationReporting = System.getProperty("user.dir") + fileSeparator + "reports" + fileSeparator;

    public FrameworkSetup() {
        loadSystemProperties();
    }

    @BeforeMethod(alwaysRun = true)
    public void startDriver() {
        if (settings.getProperty("run").equalsIgnoreCase("local")) {
            startLocalDriver();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void stopDriver() {
        driver().quit();
    }

    public WebDriver driver() {
        return DriverThreadLocal.getDriver();
    }

    private void startLocalDriver() {
        WebDriver driver;
        ChromeOptions chromeOptions;
        if (browser.equalsIgnoreCase("Chrome")) {
            chromeOptions = getChromeOptions();
            driver = new ChromeDriver(chromeOptions);
            configureDevTools(driver);
            driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("HeadlessChrome")) {
            chromeOptions = getHeadlessChromeOptions();
            driver = new ChromeDriver(chromeOptions);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        DriverThreadLocal.setDriver(driver);
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String, Object> chromePrefs = new HashMap<>();

        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", fileDirectory);
        chromePrefs.put("selectfile.last_directory", fileDirectory);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("directory_upgrade", true);

        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.addArguments("--incognito", "--remote-allow-origins=*", "ignore-certificate-errors");
        return chromeOptions;
    }

    private ChromeOptions getHeadlessChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito", "--headless", "--disable-extensions", "--window-size=1920,1080");
        return chromeOptions;
    }

    private void configureDevTools(WebDriver driver) {
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
    }

    /*
     * -------------------- Loading system specific properties and retrieving them -------------------- //
     */

    private void loadSystemProperties() {
        String systemPropertiesFile = String.format("src/main/resources/system_properties/%s_%s.properties", system, environment);
        loadProperties(systemPropertiesFile);
    }

    private void loadProperties(String propertiesFile) {
        try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);

            for (String key : properties.stringPropertyNames()) {
                systemProperties.put(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            log.error("Error loading properties file: {}", propertiesFile, e);
        }
    }

    public String getProperty(String key) {
        return systemProperties.get(key);
    }

}