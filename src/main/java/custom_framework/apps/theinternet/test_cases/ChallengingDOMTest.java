package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChallengingDOMTest extends TheInternetFlow {

    @Test
    @Description("Count number of rows and columns in table")
    @Owner("Ninna994")
    public void testCountRowsAndColumns() {
        navChallengingDOM();
        int noOfRows = driver().findElements(By.xpath("//tbody//tr")).size();
        int noOfColumns = driver().findElements(By.xpath("//thead//tr//th")).size();

        System.out.println("Table has " + noOfRows + " rows and " + noOfColumns + " columns. ");

    }

    @Test
    @Description("Delete row and with element by last column element text")
    @Owner("Ninna994")
    public void testDeleteRowWithCell() {
        navChallengingDOM();
        verifyPageTitle("Challenging DOM");
        String textToLookFor = "Phaedrum4";

        click(By.xpath("(//td[contains(text(), '" + textToLookFor + "')]//parent::tr)//a[@href='#delete']"));
    }

    @Test
    @Description("Print text of element located in #row #column")
    @Owner("Ninna994")
    public void testFindCellByRowColumn() {
        navChallengingDOM();
        verifyPageTitle("Challenging DOM");
        int row = 2;
        int column = 3;
        WebElement element = driver().findElement(theElementInTable(row, column));
        System.out.println(element.getText());
    }

    @Test
    @Description("Click on button and wait for page to load, then take screenshot of canvas element")
    @Owner("Ninna994")
    public void testPageReloadAndCanvasScreenshot() {
        navChallengingDOM();
        String startingId = getAttribute(theFirstButton(), "id");
        click(theFirstButton());
        waitForPageToLoad();
        String idAfterRefresh = getAttribute(theFirstButton(), "id");
        Assert.assertFalse(startingId.equalsIgnoreCase(idAfterRefresh), "ID should be changed");

        getScreenshotOfElement(theCanvas());
    }

}
