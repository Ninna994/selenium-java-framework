package custom_framework.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        focusIframe("html_msg_body");
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

    public void focusDefaultContent() {
        driver().switchTo().defaultContent();
    }

    public void focusElement(By by) {
        new Actions(driver()).moveToElement(driver().findElement(by)).perform();
    }

    public void focusIframe(By by) {
        waitForPageToLoad();
        WebElement element = driver().findElement(by);
        driver().switchTo().frame(element);
    }

    public void focusIframe(String frame) {
        driver().switchTo().frame(frame);
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
     * Screenshot method for adding screenshots to reports
     *
     * @param testCaseName
     * @return
     * @throws IOException
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

    public void selectRandomDropdownValue(By dropdown, By input) {
        int value = Integer.parseInt(getRandomNumber(1, 10));
        click(dropdown);
        List<WebElement> listItems = driver().findElements(input);
        listItems.get(value).click();
    }

    public void selectValue(By by, String value) {
        waitForElementClickable(by);
        Select select = new Select(driver().findElement(by));
        select.selectByValue(value);
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
     * @return
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
            return driver().findElement(by).isDisplayed();
        } catch (Exception e) {
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
     * @param by
     * @return
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

    public String getTimestamp() {
        long dateLong;
        dateLong = System.currentTimeMillis();
        return String.valueOf(dateLong);
    }

    public List<WebElement> getOptions(By dropdown) {
        waitForElementPresent(dropdown);
        return new Select(getElement(dropdown)).getOptions();
    }

    public String getUniqueValue() {
        return getTimestamp();
    }

    /*
     * -------------------- USER ACTIONS -------------------- //
     */

    public void alertDismiss() {
        driver().switchTo().alert().dismiss();
    }

    public void alertAccept() {
        driver().switchTo().alert().accept();
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

    public void deleteAllFilesFromDirectory() {
        String directory = fileDirectory;

        File file = new File(directory);
        String[] currentFiles;
        if (file.isDirectory()) {
            currentFiles = file.list();
            for (int i = 0; i < currentFiles.length; i++) {
                File myFile = new File(file, currentFiles[i]);
                if (!myFile.getName().contains("automation")) {
                    myFile.delete();
                }

            }
        }
    }

    public void downloadDocument(String fileName1) {
        sleepTime(4000);
        File folder = new File(fileDirectory);
        log.info(fileDirectory);
        File[] listOfFiles = folder.listFiles();
        boolean found = false;
        File f = null;

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String fileName = listOfFile.getName();
                log.info("File {}", listOfFile.getName());
                if (fileName.matches(fileName1)) {
                    f = new File(fileName);
                    found = true;
                    Assert.assertTrue(found, "Document not found");
                }
            }
        }
        Assert.assertTrue(found, "Downloaded document is not found");
    }

    public String findLastDownloadedFile() {
        File dir = new File(fileDirectory);
        File[] files = dir.listFiles();

        if (files == null || files.length == 0) return null;

        File lastModifiedFile = files[0];

        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) lastModifiedFile = files[i];
        }
        return lastModifiedFile.getName();
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
        driver().findElement(By.xpath(" //input[@type='file']")).sendKeys(fileDirectory + "\\" + name);
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

}
