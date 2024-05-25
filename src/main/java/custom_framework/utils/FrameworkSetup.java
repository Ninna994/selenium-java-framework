package custom_framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v124.log.Log;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    public final String fileDirectory = System.getProperty("user.dir") + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "test_data" + fileSeparator;
    public final String downloadDirectory = fileDirectory + fileSeparator + "downloads";
    public final String screenshotDestinationReporting = System.getProperty("user.dir") + fileSeparator + "reports" + fileSeparator;

    public FrameworkSetup() {
        loadSystemProperties();
    }

    public String getBrowser() {
        return browser;
    }

    @BeforeMethod(alwaysRun = true)
    public void startDriver() throws MalformedURLException {
        if (settings.getProperty("run").equalsIgnoreCase("local")) {
            startLocalDriver();
        } else if (settings.getProperty("run").equalsIgnoreCase("remote")) {
            initializeRemoteDriver();
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
        if (browser.equalsIgnoreCase("Chrome")) {
            ChromeOptions chromeOptions = getChromeOptions();
            driver = new ChromeDriver(chromeOptions);
            configureChromeDevTools((ChromeDriver) driver);  // Add this method if needed, or remove if not applicable
        } else if (browser.equalsIgnoreCase("HeadlessChrome")) {
            ChromeOptions chromeOptions = getHeadlessChromeOptions();
            driver = new ChromeDriver(chromeOptions);
            configureChromeDevTools((ChromeDriver) driver);  // Add this method if needed, or remove if not applicable
        } else if (browser.equalsIgnoreCase("Firefox")) {
            FirefoxOptions firefoxOptions = getFirefoxOptions();
            driver = new FirefoxDriver(firefoxOptions);
        } else if (browser.equalsIgnoreCase("Edge")) {
            EdgeOptions edgeOptions = getEdgeOptions();
            driver = new EdgeDriver(edgeOptions);
            configureEdgeDevTools((EdgeDriver) driver);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        // Common configurations
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().window().maximize();

        DriverThreadLocal.setDriver(driver);  // Ensure this is your own implementation to manage WebDriver instances
    }

    private void initializeRemoteDriver() throws MalformedURLException {
        WebDriver driver;
        Capabilities options;
        if (browser.equalsIgnoreCase("chrome")) {
            options = getChromeOptions();
        } else if (browser.equalsIgnoreCase("Firefox")) {
            options = getFirefoxOptions();
        } else if (browser.equalsIgnoreCase("edge")) {
            options = new EdgeOptions();
        } else if (browser.equalsIgnoreCase("HeadlessChrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--incognito", "--headless", "--disable-extensions", "--window-size=1920,1080");
            options = chromeOptions;
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver = new RemoteWebDriver(new URL("http://192.168.1.100:4444/wd/hub"), options);
        if (browser.equalsIgnoreCase("chrome")) {
            configureChromeDevTools((ChromeDriver) driver);
        }
        driver.manage().window().maximize();
        DriverThreadLocal.setDriver(driver);
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String, Object> chromePrefs = new HashMap<>();

        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadDirectory);
        chromePrefs.put("selectfile.last_directory", fileDirectory);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("directory_upgrade", true);

        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.addArguments("--incognito", "--remote-allow-origins=*", "ignore-certificate-errors", "disable-features=DownloadBubble,DownloadBubbleV2");
        return chromeOptions;
    }

    private EdgeOptions getEdgeOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();
        Map<String, Object> edgePrefs = new HashMap<>();

        edgePrefs.put("profile.default_content_settings.popups", 0);
        edgePrefs.put("download.default_directory", downloadDirectory);
        edgePrefs.put("selectfile.last_directory", fileDirectory);
        edgePrefs.put("download.prompt_for_download", false);
        edgePrefs.put("directory_upgrade", true);

        edgeOptions.setExperimentalOption("prefs", edgePrefs);
        edgeOptions.addArguments("--inprivate", "--remote-allow-origins=*", "ignore-certificate-errors", "disable-features=DownloadBubble,DownloadBubbleV2");
        return edgeOptions;
    }

    private FirefoxOptions getFirefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        firefoxOptions.addPreference("browser.download.dir", downloadDirectory);
        firefoxOptions.addPreference("browser.download.folderList", 2);
        firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
        firefoxOptions.addPreference("pdfjs.disabled", true); // Disable the built-in PDF viewer

        return firefoxOptions;
    }

    private ChromeOptions getHeadlessChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito", "--headless", "--disable-extensions", "--window-size=1920,1080");
        return chromeOptions;
    }

    private void configureChromeDevTools(ChromeDriver driver) {
        DevTools devTools = (driver).getDevTools();
        devTools.createSession();
        devTools.send(Log.enable());
        devTools.addListener(Log.entryAdded(), entryAdded -> {
            String logText = entryAdded.getText();
            String logUrl = String.valueOf(entryAdded.getUrl());
            String logLevel = entryAdded.getLevel().toString();
            if (logLevel.equals("warning") || logLevel.equals("error") && !logText.contains("net::ERR_NAME_NOT_RESOLVED")) {
                String consoleLogMessage = ("CONSOLE LOGGED " + logLevel.toUpperCase() + ". \n Log message ==> " + logText + "\n" + "Error url ==> " + logUrl);
                log.error(consoleLogMessage);
            }
        });
    }

    private void configureEdgeDevTools(EdgeDriver driver) {
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Log.enable());
        devTools.addListener(Log.entryAdded(), entryAdded -> {
            String logText = entryAdded.getText();
            String logUrl = String.valueOf(entryAdded.getUrl());
            String logLevel = entryAdded.getLevel().toString();
            if (logLevel.equals("warning") || logLevel.equals("error") && !logText.contains("net::ERR_NAME_NOT_RESOLVED")) {
                String consoleLogMessage = ("CONSOLE LOGGED " + logLevel.toUpperCase() + ". \n Log message ==> " + logText + "\n" + "Error url ==> " + logUrl);
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