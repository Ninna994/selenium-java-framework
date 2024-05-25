package custom_framework.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v124.emulation.Emulation;
import org.openqa.selenium.devtools.v124.network.Network;
import org.openqa.selenium.devtools.v124.network.model.ConnectionType;
import org.openqa.selenium.devtools.v124.network.model.Headers;
import org.openqa.selenium.devtools.v124.performance.Performance;
import org.openqa.selenium.devtools.v124.performance.model.Metric;
import org.openqa.selenium.devtools.v124.security.Security;
import org.openqa.selenium.devtools.v124.storage.Storage;
import org.openqa.selenium.devtools.v85.fetch.Fetch;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


public class SharedMethods extends FrameworkSetup {

    Random randomNum = new Random();

    /**
     * Checks a checkbox or selects a radio button if it is not already selected
     *
     * @param by the locator used to identify the checkbox or radio button element
     */
    public void check(By by) {
        sleepTime(1000);
        waitForElementClickable(by);
        if (!driver().findElement(by).isSelected()) {
            click(by);
        }
    }

    /**
     * Checks a checkbox or selects a radio after some time has passed before clicking occurs
     *
     * @param by     - what to Click
     * @param msTime - how long to wait after click
     */
    public void check(By by, long msTime) {
        sleepTime(1000);
        waitForElementClickable(by);
        if (!driver().findElement(by).isSelected()) {
            click(by, msTime);
        }
    }

    /**
     * Helper method to handle mailinator for disposable emails. It visits mailinator website, searches for mailbox and clicks on button in email
     *
     * @param email - actual mailbox that needs to be checked
     */
    public void checkMailinatorInbox(String email) {
        inputUrl("https://mailinator.com");
        waitForPageToLoad();
        input(By.id("search"), email);
        click(By.value("Search for public inbox for free"));
        sleepTime(2000);
        click(By.cssSelector("tr.ng-scope:first-child"));
        focusFrame("html_msg_body");
        scrollToBottom();
        click(1000, By.tagName("button"), 1000);
        focusNewTab();
        sleepTime(1000);
    }

    /**
     * Method that empties input element if something is already inserted in it
     *
     * @param by the locator used to identify the element
     */
    public void clearField(By by) {
        click(by);
        driver().findElement(by).clear();
    }

    /**
     * Click on element. Performs most stable click - the one from Actions class
     *
     * @param by - the locator used to identify the element
     */
    public void click(By by) {
        sleepTime(1000);
        waitForElementClickable(by);
        new Actions(driver()).moveToElement(driver().findElement(by)).click().build().perform();
    }

    /**
     * Provide time needed for sleep BEFORE clicking and click on element by providing locator of element
     *
     * @param msTime - define sleepTime
     * @param by     - the locator used to identify the element
     */
    public void click(long msTime, By by) {
        sleepTime(msTime);
        click(by);
    }

    /**
     * Click on element by providing locator of element and Provide time needed for sleep AFTER clicking occurs
     *
     * @param msTime - define sleepTime
     * @param by     - the locator used to identify the element
     */
    public void click(By by, long msTime) {
        click(by);
        sleepTime(msTime);
    }

    /**
     * Wait, then click on element by UI locator and then wait again
     *
     * @param msTimeBefore - how long to wait for before clicking
     * @param by           - the locator used to identify the element
     * @param msTimeAfter  - how long to wait after click
     */
    public void click(long msTimeBefore, By by, long msTimeAfter) {
        sleepTime(msTimeBefore);
        click(by);
        sleepTime(msTimeAfter);
    }

    /**
     * Click on element by providing locator of element that is clicked and then wait for other element to appear
     *
     * @param byToClick   - what to click
     * @param byToWaitFor - what to wait for
     */
    public void click(By byToClick, By byToWaitFor) {
        click(byToClick);
        new WebDriverWait(driver(), Duration.ofMillis(timeout)).until(ExpectedConditions.visibilityOfElementLocated(byToWaitFor));
    }

    /**
     * Method used to wait for page to load after element is clicked
     *
     * @param by - the locator used to identify the element
     */
    public void clickAndWaitPageToLoad(By by) {
        click(by);
        waitForPageToLoad();
        sleepTime(1000);
    }

    /**
     * Method used to achieve clicking on actual XY pixel on screen
     *
     * @param by - the locator used to identify the element
     * @param x  - X axis point
     * @param y  - Y axis point
     */
    public void clickByPixel(By by, int x, int y) {
        Actions builder = new Actions(driver());
        builder.moveToElement(driver().findElement(by), x, y).click().build().perform();
    }

    /**
     * Method used to perform JS click on elements
     *
     * @param by - the locator used to identify the element
     */
    public void clickJs(By by) {
        WebElement element = driver().findElement(by);
        JavascriptExecutor jse = (JavascriptExecutor) driver();
        jse.executeScript("arguments[0].click();", element);
        sleepTime(1000);
    }

    public void contextClickOnElement(By by) {
        WebElement element = driver().findElement(by);
        Actions actions = new Actions(driver());
        actions.contextClick(element).perform();
    }

    public void dragAndDrop(By by1, By by2) {
        WebElement fromElement = driver().findElement(by1);
        WebElement toElement = driver().findElement(by2);
        Actions builder = new Actions(driver());
        sleepTime(1000);
        builder.dragAndDrop(fromElement, toElement).build().perform();
        sleepTime(2000);
    }

    public void focusActiveElement() {
        driver().switchTo().activeElement();
    }

    public void focusActiveWindow() {
        for (String winHandle : driver().getWindowHandles()) {
            driver().switchTo().window(winHandle);
        }
    }

    public void focusActiveWindow(String windowTitle) {
        Set<String> windows = driver().getWindowHandles();
        for (String window : windows) {
            driver().switchTo().window(window);
            if (driver().getTitle().contains(windowTitle)) {
                return;
            }
        }
    }

    public void focusAlert() {
        driver().switchTo().alert();
    }

    public void focusDefaultContent() {
        driver().switchTo().defaultContent();
    }

    public void focusElement(By by) {
        new Actions(driver()).moveToElement(driver().findElement(by)).perform();
    }

    public void focusFrame(By by) {
        waitForPageToLoad();
        WebElement element = driver().findElement(by);
        driver().switchTo().frame(element);
    }

    public void focusFrame(String frame) {
        driver().switchTo().frame(frame);
    }

    public void focusFrameParent() {
        driver().switchTo().parentFrame();
    }

    public void focusNewTab() {
        Set<String> windows = driver().getWindowHandles();
        Iterator<String> it = windows.iterator();
        String originalTab = it.next();
        log.info("Original tab {}", originalTab);
        String newTab = it.next();
        driver().switchTo().window(newTab);
        sleepTime(2000);
    }

    public String getAttribute(By by, String attributeType) {
        waitForElementPresent(by);
        return driver().findElement(by).getAttribute(attributeType);
    }

    /**
     * Method for taking element screenshots by providing locator
     *
     * @param by - locator of element
     */
    public void getScreenshotOfElement(By by) {
        try {
            File screenshot = driver().findElement(by).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(screenshotDestinationReporting + fileSeparator + "screenshots" + fileSeparator + "element_screenshot" + getTimestamp() + ".png");
            FileUtils.copyFile(screenshot, destinationFile);

            log.info("Screenshot of the element was saved as: {}", destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Screenshot method for adding screenshots to reports
     *
     * @param testCaseName - name of TC
     * @return - destination File printed
     */
    public String getScreenshotPath(String testCaseName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationFile = screenshotDestinationReporting + testCaseName + ".jpg";
        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;
    }

    public void getText(By by) {
        waitForElementVisible(by);
        driver().findElement(by).getText();
    }

    public void hoverAndClick(By byHover, By byClick) {
        waitForElementClickable(byHover);
        Actions action = new Actions(driver());
        action.moveToElement(driver().findElement(byHover)).build().perform();
        action.moveToElement(driver().findElement(byClick)).click().build().perform();
    }

    public void hoverAndClick(By byHover, String textToClickOn) {
        waitForElementClickable(byHover);
        sleepTime(500);
        Actions action = new Actions(driver());
        action.moveToElement(driver().findElement(byHover)).build().perform();
        action.moveToElement(driver().findElement(By.xpath("//*[contains(text(), '" + textToClickOn + "')]"))).click().build().perform();
    }

    public void hoverOver(By by) {
        waitForElementPresent(by);
        new Actions(driver()).moveToElement(driver().findElement(by)).perform();
    }

    public void hoverOver(WebElement element) {
        new Actions(driver()).moveToElement(element).perform();
    }

    public void input(By by, String text) {
        waitForElementClickable(by);
        driver().findElement(by).clear();
        driver().findElement(by).sendKeys(text);
    }

    public void inputSuperClean(By by, String text) {
        waitForElementClickable(by);
        click(by);
        driver().findElement(by).sendKeys(Keys.CONTROL, "a");
        driver().findElement(by).sendKeys(Keys.DELETE);
        driver().findElement(by).sendKeys(text);
    }

    public void inputUrl(String url) {
        driver().get(url);
    }

    public void pressEnterKey(By by) {
        driver().findElement(by).sendKeys(Keys.ENTER);
    }

    public void pressTabKey() {
        driver().findElement(By.tagName("body")).sendKeys(Keys.TAB);
    }

    public void select(By by, String text) {
        sleepTime(1000);
        waitForElementClickable(by);
        Select select = new Select(driver().findElement(by));
        select.selectByVisibleText(text);
    }

    public void select(By dropdown, By option) {
        click(dropdown);
        click(option);
    }

    public void selectByIndex(By dropdown, int index) {
        sleepTime(1000);
        waitForElementClickable(dropdown);
        Select select = new Select(driver().findElement(dropdown));
        select.selectByIndex(index);
    }

    public void selectByValue(By by, String value) {
        waitForElementClickable(by);
        Select select = new Select(driver().findElement(by));
        select.selectByValue(value);
    }

    public void selectRandomDropdownValue(By dropdown, By input) {
        int value = Integer.parseInt(getRandomNumber(1, 10));
        click(dropdown);
        List<WebElement> listItems = driver().findElements(input);
        listItems.get(value).click();
    }

    public void uncheck(By by) {
        waitForElementClickable(by);
        if (driver().findElement(by).isSelected()) {
            driver().findElement(by).click();
        }
    }

    /*
     * -------------------- BOOLEAN / VERIFY METHODS -------------------- //
     */

    public boolean isAlertPresent() {
        try {
            driver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException ex) {
            return false;
        }
    }

    public boolean isCheckboxChecked(By by) {
        waitForElementVisible(by);
        return driver().findElement(by).isSelected();
    }

    /**
     * Function that returns true if option is found inside select options and false if no options matching are found
     *
     * @param selectElement - select element from DOM
     * @param optionValue   - option value we want to find
     */
    public boolean isDropdownValuePresent(By selectElement, String optionValue) {
        waitForElementVisible(selectElement);
        Select select = new Select(driver().findElement(selectElement));
        boolean isFound = false;

        List<WebElement> allOptions = select.getOptions();
        for (WebElement allOption : allOptions) {
            log.info("Option found: {}, compared to: {}", allOption.getText(), optionValue);
            if (allOption.getText().contains(optionValue)) {
                isFound = true;
                break;
            }
        }
        return isFound;
    }

    public boolean isElementDisplayed(By by) {
        return getElement(by).isDisplayed();
    }

    public boolean isElementEnabled(By by) {
        waitForElementVisible(by);
        return getElement(by).isEnabled();
    }

    public boolean isElementPresent(By by) {
        try {
            driver().findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementSelected(By by) {
        waitForElementVisible(by);
        String checked = driver().findElement(by).getAttribute("checked");
        return checked.contains("true");
    }

    public boolean isFieldReadOnly(By by) {
        waitForElementVisible(by);
        return driver().findElement(by).getAttribute("readonly") != null;
    }

    public boolean isFieldValuePresent(By by, String value) {
        waitForElementVisible(by);
        return driver().findElement(by).getAttribute("value").contains(value);
    }

    public boolean isTextPresent(String text, By by) {
        return new WebDriverWait(driver(), Duration.ofSeconds(timeout)).until(ExpectedConditions.textToBePresentInElementLocated(by, text));
    }

    public String verifyAttribute(By by, String attribute) {
        waitForElementVisible(by);
        try {
            return driver().findElement(by).getAttribute(attribute);
        } catch (Exception e) {
            return e.toString();
        }
    }

    public int verifyCount(By by) {
        waitForElementVisible(by);
        return driver().findElements(by).size();
    }

    public String verifySelectedValue(By by) {
        waitForElementVisible(by);
        return new Select(getElement(by)).getFirstSelectedOption().getText();
    }

    public String verifyText(By by) {
        waitForElementVisible(by);
        try {
            return driver().findElement(by).getText();
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String verifyTextFieldValue(By by) {
        return verifyAttribute(by, "value");
    }

    /*
     * -------------------- GETTERS -------------------- //
     */

    /**
     * getElement function that accepts zero or more By arguments and returns true if element is present
     *
     * @param by - what to search for
     */
    public WebElement getElement(By... by) {
        WebElement element;
        try {
            element = driver().findElement(by[0]);
            if (by.length > 0) {
                for (int i = 0; i < by.length; i++) {
                    element = driver().findElement(by[i]);
                }
            }
        } catch (Exception ex) {
            return null;
        }
        return element;
    }

    public WebElement getFirstElement(By by) {
        return driver().findElements(by).get(0);
    }

    public WebElement getLastElement(By by) {
        return driver().findElements(by).get(driver().findElements(by).size() - 1);
    }

    public List<WebElement> getOptions(By dropdown) {
        waitForElementPresent(dropdown);
        return new Select(getElement(dropdown)).getOptions();
    }

    public String getRandomNumber(int min, int max) {
        return RandomStringUtils.randomNumeric(min, max);
    }

    public static String getRandomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public String getRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public String getSelectedValue(By dropdown) {
        waitForElementVisible(dropdown);
        return new Select(getElement(dropdown)).getFirstSelectedOption().getText();
    }

    public SearchContext getShadowRoot(By by) {
        return driver().findElement(by).getShadowRoot();
    }

    public String getTimestamp() {
        long dateLong;
        dateLong = System.currentTimeMillis();
        return String.valueOf(dateLong);
    }

    public String getUniqueValue() {
        return getTimestamp();
    }

    /*
     * -------------------- USER ACTIONS -------------------- //
     */

    public void alertAccept() {
        driver().switchTo().alert().accept();
    }

    public void alertDismiss() {
        driver().switchTo().alert().dismiss();
    }

    public void closeActiveBrowserTab() {
        driver().close();
    }

    public void closeBrowser() {
        driver().quit();
    }

    public void closeBrowserAlert() {
        if (isAlertPresent()) {
            Alert alert = driver().switchTo().alert();
            alert.accept(); //clicks the "Okay" button
            sleepTime(1000);
        }
    }

    /**
     * OS specific method - for windows and for MAC
     */
    public void closeBrowserTab() {
        String os = System.getProperty("os.name").toLowerCase();
        Actions builder = new Actions(driver());
        if (os.contains("windows")) {
            Actions select = builder.keyDown(Keys.CONTROL).sendKeys("w");
            select.perform();
        } else {
            Actions select = builder.keyDown(Keys.COMMAND).sendKeys("w");
            builder.keyUp(Keys.COMMAND);
            select.perform();
        }
    }

    public void closeTab() {
        Set<String> handlesSet = driver().getWindowHandles();
        List<String> handlesList = new ArrayList<>(handlesSet);
        driver().switchTo().window(handlesList.get(1));
        driver().close();
        driver().switchTo().window(handlesList.get(0));
    }

    public void maximizeWindow() {
        driver().manage().window().maximize();
    }

    public void navigateOutsideViewport() {
        String script = "var event = new Event('mouseleave', { bubbles: true, cancelable: true }); " +
                "document.querySelector('body').dispatchEvent(event);";
        ((JavascriptExecutor) driver()).executeScript(script);
    }

    public void refreshPage() {
        driver().navigate().refresh();
    }

    public void scrollDown() {
        JavascriptExecutor jse = ((JavascriptExecutor) driver());
        jse.executeScript("window.scrollBy(0,500)", "");
    }

    public void scrollIntoView(By by) {
        JavascriptExecutor jse = (JavascriptExecutor) driver();
        jse.executeScript("arguments[0].scrollIntoView(true);", driver().findElement(by));
    }

    public void scrollJs(int x, int y) {
        JavascriptExecutor jse = (JavascriptExecutor) driver();
        jse.executeScript("window.scrollBy(" + x + "," + y + ")");
    }

    public void scrollToBottom() {
        driver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
    }

    public void scrollToBottomOfPageJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver();
        js.executeScript("window.scrollBy(0, document.body.scrollHeight)", "");
    }

    public void scrollToTop() {
        driver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.HOME);
    }

    public void scrollToTopOfPageJs() {
        JavascriptExecutor jse = (JavascriptExecutor) driver();
        jse.executeScript("window.scrollBy(0, -document.body.scrollHeight)", "");
    }

    public void setBrowserSize(int width, int height) {
        Dimension d = new Dimension(width, height);
        driver().manage().window().setSize(d);
    }

    /*
     * -------------------- WAIT METHODS -------------------- //
     */

    public void sleepTime(long param) {
        try {
            Thread.sleep(param);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt(); // Re-interrupt the thread
            Assert.fail("InterruptedException: " + ex.getMessage());
        }
    }

    public void waitForElementClickable(By by) {
        new WebDriverWait(driver(), Duration.ofSeconds(timeout)).until(ExpectedConditions.elementToBeClickable(by));
    }

    public void waitForElementEnabled(By by) {
        new WebDriverWait(driver(), Duration.ofSeconds(timeout)).until(ExpectedConditions.attributeToBe(by, "disabled", ""));
    }

    public void waitForElementInvisible(By by) {
        new WebDriverWait(driver(), Duration.ofSeconds(timeout)).until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void waitForElementPresent(By by) {
        new WebDriverWait(driver(), Duration.ofSeconds(timeout)).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitForElementVisible(By by) {
        new WebDriverWait(driver(), Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForPageToLoad() {
        driver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
    }

    /*
     * -------------------- FILES METHODS -------------------- //
     */

    public void deleteAllFilesFromDownloadDirectory() {
        Path directory = Paths.get(downloadDirectory);
        if (Files.isDirectory(directory)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                for (Path file : stream) {
                    try {
                        Files.delete(file);
                        System.out.println("Deleted: " + file.getFileName());
                    } catch (IOException e) {
                        System.err.println("Failed to delete: " + file.getFileName() + " due to " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Error accessing directory: " + e.getMessage());
            }
        } else {
            System.err.println("Not a directory: " + downloadDirectory);
        }
    }

    public String findLastDownloadedFile() {
        File dir = new File(downloadDirectory);
        File[] files = dir.listFiles();

        if (files == null || files.length == 0) return null;

        File lastModifiedFile = Arrays.stream(files)
                .filter(File::isFile) // Ensure we only consider files
                .max(Comparator.comparingLong(File::lastModified))
                .orElse(null);

        return lastModifiedFile != null ? lastModifiedFile.getName() : null;
    }

    public int getFileCount() {
        File dir = new File(downloadDirectory);
        File[] files = dir.listFiles();
        return (files != null) ? files.length : 0;
    }

    public String readExcel(int columnNumber) throws IOException {
        FileInputStream fs = new FileInputStream(fileDirectory + "\\" + "proba.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheetAt(0); //sheet number
        Row row = sheet.getRow(1);
        Cell cell = row.getCell(columnNumber);
        log.info("Broj kolone: {}", columnNumber);
        return String.valueOf(cell);
    }

    public void uploadFile(String name) {
        driver().findElement(By.xpath("//input[@type='file']")).sendKeys(fileDirectory + "\\" + name);
    }

    public void uploadFileJS(String name) {
        JavascriptExecutor js = (JavascriptExecutor) driver();
        WebElement fileInput = driver().findElement(By.xpath("//input[@type='file']"));
        js.executeScript("arguments[0].value='" + fileDirectory + "\\" + name + "';", fileInput);
    }

    public void uploadTwoFiles(String fileName1, String fileName2) {
        driver().findElement(By.xpath(" //input[@type='file']")).sendKeys(fileDirectory + "\\" + fileName1 + "\n " + fileDirectory + "\\" + fileName2);
    }

    /*
     * -------------------- CALENDAR / DATE / TIME METHODS -------------------- //
     */

    public String getCurrentDate() {
        Date todayDate = getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy.");
        return format1.format(todayDate);
    }

    public String getCurrentTime() {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Belgrade"));
        int hour = cal.get(Calendar.HOUR);
        int minutes = cal.get(Calendar.MINUTE);
        return hour + ":" + minutes;
    }

    public Date getDate() {
        Calendar cal = GregorianCalendar.getInstance();
        return cal.getTime();
    }

    public String getDateInFuture(String originalDate, int numberOfDays) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy.");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format1.parse(originalDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, numberOfDays);
        return format1.format(calendar.getTime());
    }

    public String getDateTimeStamp() {
        return new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
    }

    /*
     * -------------------- SELENIUM 4 -------------------- //
     */
    DevTools devTools = null;
    FrameworkSetup fs = new FrameworkSetup();
    String browser = fs.getBrowser();

    public void handleDevTools() {
        if (browser.equalsIgnoreCase("chrome")) {
            devTools = ((ChromeDriver) driver()).getDevTools();
        } else if (browser.equalsIgnoreCase("edge")) {
            devTools = ((EdgeDriver) driver()).getDevTools();
        }
        assert devTools != null;
        devTools.createSession();
    }

    public void mockGeolocation(double latitude, double longitude, int accuracy) {

        handleDevTools();
        devTools.send(Emulation.setGeolocationOverride(Optional.of(latitude),
                Optional.of(longitude),
                Optional.of(accuracy)));
        driver().get("https://my-location.org/");
        sleepTime(5000);
    }

    /*
     * -------------------- NETWORK METHODS -------------------- //
     */

    public void blockUrls() {
        handleDevTools();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.send(Network.setBlockedURLs(List.of("*.css"))); // Blocks all css files
            /*
             * Block request by URL below code
             devTool.send(Network.setBlockedURLs( List.of(
             "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"
              )));

             */

        devTools.addListener(Network.loadingFailed(), loadingFailed -> System.out.println("Blocking reason: " + loadingFailed.getBlockedReason().get()));

        inputUrl("https://rahulshettyacademy.com/#/index");
        sleepTime(5000);
    }

    public void bypassInsecureWebsite() {
        handleDevTools();
        devTools.send(Security.setIgnoreCertificateErrors(true));

        inputUrl("https://untrusted-root.badssl.com/");
    }

    public void captureRequest() {
        handleDevTools();

        // enable network capture
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(), requestWillBeSent -> {
            System.out.println("Request url: " + requestWillBeSent.getDocumentURL());
            System.out.println("Request method: " + requestWillBeSent.getRequest().getMethod());
            System.out.println("Request Headers: " + requestWillBeSent.getRequest().getHeaders().toString());
            System.out.println("-------------------------------------------------");
        });

        // navigate to url
        inputUrl("https://rahulshettyacademy.com/#/index");
    }

    public void captureResponse() {
        handleDevTools();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.responseReceived(), responseReceived -> printResponseDetails(
                responseReceived.getResponse().getUrl(),
                responseReceived.getResponse().getStatus(),
                responseReceived.getResponse().getHeaders().toString(),
                responseReceived.getResponse().getMimeType()
        ));

        // navigate to url
        //inputUrl("https://google.com");
    }

    public void captureResponse(String responsePartUrl) {
        handleDevTools();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            if (responseReceived.getResponse().getUrl().contains(responsePartUrl)) {
                printResponseDetails(
                        responseReceived.getResponse().getUrl(),
                        responseReceived.getResponse().getStatus(),
                        responseReceived.getResponse().getHeaders().toString(),
                        responseReceived.getResponse().getMimeType()
                );
            }
        });
    }

    public void captureResponse(int responseCode) {
        handleDevTools();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            if (responseReceived.getResponse().getStatus().equals(responseCode)) {
                printResponseDetails(
                        responseReceived.getResponse().getUrl(),
                        responseReceived.getResponse().getStatus(),
                        responseReceived.getResponse().getHeaders().toString(),
                        responseReceived.getResponse().getMimeType()
                );
            }
        });
    }

    public void clearCache() {
        handleDevTools();
        devTools.send(Network.clearBrowserCache());
    }

    public void clearStorage(String originUrl) {
        handleDevTools();
        String storageTypes = "cookies,local_storage,session_storage,indexeddb,cache_storage";
        devTools.send(Storage.clearDataForOrigin(
                originUrl,
                storageTypes));
    }

    public void emulateNetwork() {
        handleDevTools();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.send(Network.emulateNetworkConditions(false, 20, 20, 50, Optional.of(ConnectionType.CELLULAR3G), Optional.empty(), Optional.empty(), Optional.empty()));
        inputUrl("https://rahulshettyacademy.com/#/index");
    }

    public int getUrlResponseCode(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("HEAD");
        connection.connect();
        return connection.getResponseCode();
    }

    public void metrics() {
        handleDevTools();

        devTools.send(Performance.enable(Optional.empty()));
        List<Metric> metricList = devTools.send(Performance.getMetrics());
        driver().get("https://opensource.saucelabs.com/");

        for (Metric m : metricList) {
            System.out.println(m.getName() + " = " + m.getValue());
        }

    }

    public void mockAPIRequest() {
        handleDevTools();

        devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));

        devTools.addListener(Fetch.requestPaused(), req -> {
            if (req.getRequest().getUrl().contains("=Shetty")) {
                String mock = req.getRequest().getUrl().replace("=shetty", "=Unknown");
                devTools.send(Fetch.continueRequest(req.getRequestId(), Optional.of(mock), Optional.empty(),
                        Optional.empty(), Optional.empty()));
            } else {
                devTools.send(Fetch.continueRequest(req.getRequestId(), Optional.of(req.getRequest().getUrl()),
                        Optional.empty(), Optional.empty(), Optional.empty()));
            }
        });

        driver().get("https://www.rahulshettyacademy.com/angularAppdemo/");

        driver().findElement(By.xpath("//button[contains(text(),'Virtual Library')] ")).click();

    }

    public void network() {
        handleDevTools();

        driver().get("https://manytools.org/http-html-text/http-request-headers/");
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        Headers headers = new Headers(Collections.singletonMap("testing", "selenium"));
        devTools.send(Network.setExtraHTTPHeaders(headers));

        driver().get("https://manytools.org/http-html-text/http-request-headers/");
        driver().quit();
    }

    private void printResponseDetails(String url, int status, String headers, String mimeType) {
        System.out.println("------------------------------------------------------");
        System.out.println("Response Url => " + url);
        System.out.println("Response Status => " + status);
        System.out.println("Response Headers => " + headers);
        System.out.println("Response MIME Type => " + mimeType);
        System.out.println("------------------------------------------------------");
    }

}
