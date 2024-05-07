package custom_framework.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

    public void check(By by) {
        sleepTime(1000);
        waitForElementClickable(by);
        if (!driver().findElement(by).isSelected()) {
            click(by);
        }
    }

    /**
     * Check element after some time has passed before clicking occurs
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

    public void clearField(By by) {
        click(by);
        driver().findElement(by).clear();
    }

    /**
     * Click on element by providing UI element
     *
     * @param by
     */
    public void click(By by) {
        sleepTime(1000);
        waitForElementClickable(by);
        new Actions(driver()).moveToElement(driver().findElement(by)).click().build().perform();
    }

    /**
     * Provide time needed for sleep BEFORE clicking and  click on element by providing locator of element
     *
     * @param msTime - define sleepTime
     * @param by
     */
    public void click(long msTime, By by) {
        sleepTime(msTime);
        click(by);
    }

    /**
     * Click on element by providing locator of element and Provide time needed for sleep AFTER clicking and
     *
     * @param msTime - define sleepTime
     * @param by
     */
    public void click(By by, long msTime) {
        click(by);
        sleepTime(msTime);
    }

    /**
     * Wait, then click on element by UI locator and then wait again
     *
     * @param msTimeBefore - hor long to wait for before clicking
     * @param by           - what to click
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

    public void clickAndWaitPageToLoad(By by) {
        click(by);
        waitForPageToLoad();
        sleepTime(1000);
    }

    public void clickByPixel(By by, int x, int y) {
        Actions builder = new Actions(driver());
        builder.moveToElement(driver().findElement(by), x, y).click().build().perform();
    }

    public void clickJs(By by) {
        WebElement element = driver().findElement(by);
        JavascriptExecutor jse = (JavascriptExecutor) driver();
        jse.executeScript("arguments[0].click();", element);
        sleepTime(1000);
    }

    /**
     * Selects option from React component IF NO SCROLL IS DISPLAYED
     *
     * @param optionText
     */
    public void clickReactSelect(String optionText) {
        sleepTime(1000);
        click(By.xpath("//div[contains(@id, 'react-select')]//span[contains(text(), '" + optionText + "')]"));
        sleepTime(1000);
    }

    /**
     * Selects option from dropdown after clicking on some element and searching for optionText. If that optionText cannot be found error will be thrown
     *
     * @param clickBy    - what to click on
     * @param optionText - what to search from and what option to choose
     */
    public void clickReactSelectAfterSearch(By clickBy, String optionText) {
        sleepTime(1000);
        input(clickBy, optionText);
        sleepTime(1000);
        try {
            clickReactSelect(optionText);
        } catch (Exception e) {
            Assert.fail("No such option present, you tried to select: " + optionText + " and it is not part of the: " + clickBy + " element.");
        }
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

    /**
     * Specific for elements whose dropdown opens while hovering. On hover, it randomly chooses one of the elements from dropdown.
     *
     * @param byHover - element that is being hovered
     */
    public void hoverAndClickRandom(By byHover) {
        waitForElementClickable(byHover);
        sleepTime(300);
        Actions action = new Actions(driver());
        action.moveToElement(driver().findElement(byHover)).build().perform();
        waitForElementVisible(By.cssSelector(".infinite-dropdown.show"));
        List<WebElement> optionItems = driver().findElements(By.xpath("//div[contains(@class,'show')]//div[contains(@class,'dropdown')]"));

        int valMax = optionItems.size() - 1;
        int value;
        System.out.println("Number of options is: " + valMax);
        if (optionItems.isEmpty()) {
            log.error("List is empty");
        }

        if (valMax < 1) {
            value = 0;
        } else {
            value = 1 + randomNum.nextInt(valMax);
        }
        optionItems.get(value).click();
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

    public void selectAntOption(By clickBy, By clickBy1, String optionText) {
        sleepTime(500);
        click(clickBy);
        input(clickBy1, optionText);
        click(By.xpath("//span[@title='" + optionText + "']"));
        click(clickBy);
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

    public boolean isCheckboxNotChecked(By by) {
        waitForElementVisible(by);
        return !isCheckboxChecked(by);
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

    public boolean isElementDisabled(By by) {
        return !isElementEnabled(by);
    }

    public boolean isElementDisabledReact(By by) {
        return driver().findElement(by).getAttribute("class").contains("disabled");
    }

    public boolean isElementDisplayed(By by) {
        return getElement(by).isDisplayed();
    }

    public boolean isElementEnabled(By by) {
        waitForElementVisible(by);
        return getElement(by).isEnabled();
    }

    /**
     * Checks if element is not present. It waits implicitly for 30seconds for element to appear, if no element is displayed false is returned
     *
     * @param by
     * @return true if element is not present
     */
    public boolean isElementNotPresent(By by) {
        boolean value;
        driver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        List<WebElement> elementList = driver().findElements(by);
        value = elementList.isEmpty();
        driver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        return value;
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

    public boolean isFieldNotReadOnly(By by) {
        waitForElementVisible(by);
        return !isFieldReadOnly(by);
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

    public void scrollToEndOfTable() {
        waitForElementPresent(By.xpath("//div[contains(@class, 'table-container')]"));

        JavascriptExecutor jse = (JavascriptExecutor) driver();
        String jsElementIsScrollable =
                "return arguments[0].scrollHeight > arguments[0].offsetHeight;";
        WebElement container = driver().findElement(By.xpath("//div[contains(@class, 'table-container')]"));
        Boolean isScrollable = (Boolean) jse.executeScript(jsElementIsScrollable, container);

        if (Boolean.TRUE.equals(isScrollable)) {
            log.info("Table is scrollable.");
            jse.executeScript(
                    "var timer = setInterval(myFunction, 1000);" +
                            "var elem = document.querySelector('[class*=\"table-container\"]');" +
                            "var oldHeight = 0;" +
                            "var newHeight = null;" +
                            "function myFunction() {" +
                            "  if(oldHeight == newHeight) {" +
                            " clearInterval(timer);" +
                            "  return;" +
                            " } else {" +
                            "console.log(\"Old + \" + oldHeight + \"  new H >> \" + newHeight);" +
                            " oldHeight = elem.scrollHeight;" +
                            "elem.scroll({ top: elem.scrollHeight, behavior: \"smooth\"});" +
                            "setTimeout(() => {" +
                            "newHeight = elem.scrollHeight;" +
                            "console.log(newHeight)" +
                            "}, 10000)" +
                            "}" +
                            "}"
            );
            sleepTime(10000);
        } else {
            log.info("Table not scrollable");
            sleepTime(1000);
        }
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

    public void emulateMobileDevice(String mobileDevice) {
        closeBrowser();
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", mobileDevice);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        WebDriver driver = driver();
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", fileDirectory);
        chromeOptions.addArguments("--incognito");
        driver = new ChromeDriver(chromeOptions);
        DriverThreadLocal.setDriver(driver);
        focusActiveWindow();
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

    /**
     * This method validates the value between the data in excel and the table.
     * With the assertion, we validate that the difference in the tables is not more than 1.
     *
     * @throws IOException
     */

    public void validateExcel() throws IOException {
        //COUNT EXCEL TABLE ROWS
        FileInputStream fis = new FileInputStream(fileDirectory + findLastDownloadedFile());
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet0");
        int excelRowNum = sheet.getLastRowNum();
        log.info("Total number of rows in the excel is {}", excelRowNum);
        //COUNT WEB TABLE ROWS
        scrollToEndOfTable();
        List<WebElement> rowsTable = driver().findElements(By.cssSelector("tr.ant-table-row"));
        int tableRowCount = rowsTable.size();
        log.info("Number of rows in table: {}", tableRowCount);
        Assert.assertTrue(Math.abs(excelRowNum - tableRowCount) <= 1, "Number of rows in excel and table do not match");
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
