package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContextMenuTest extends TheInternetFlow {
    @Test
    @Description("Verify elements on page")
    @Owner("Ninna994")
    public void testVerifyElementsOnPage() {
        navContextMenu();
        verifyPageTitle("Context Menu");
        Assert.assertTrue(isElementDisplayed(theHotspotContainer()), "The hotspot container not displayed");
    }

    @Test
    @Description("Verify context menu")
    @Owner("Ninna994")
    public void testVerifyContextMenu() {
        navContextMenu();
        contextClickOnElement(theHotspotContainer());
        sleepTime(500);
        Assert.assertTrue(isAlertPresent(), "Alert not opened");
        Alert alert = driver().switchTo().alert();

        Assert.assertTrue(alert.getText().equalsIgnoreCase("You selected a context menu"), "Text not displayed as expected");
        alertAccept();
    }
}
