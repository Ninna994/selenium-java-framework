package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JSAlertsTest  extends TheInternetFlow {
    @Test
    @Description("Handle JS Alerts")
    @Owner("Ninna994")
    public void testJsAlerts() {
        navJSAlerts();
        verifyPageTitle("JavaScript Alerts");

        String alertNotOpened = "Alert not opened";

        click(theButtonWithText("Click for JS Alert"));
        Assert.assertTrue(isAlertPresent(), alertNotOpened);
        Assert.assertEquals(driver().switchTo().alert().getText(), "I am a JS Alert");
        alertAccept();
        Assert.assertEquals(verifyText(theResultContainer()), "You successfully clicked an alert", "Alert not successfully clicked");

        click(theButtonWithText("Click for JS Confirm"));
        Assert.assertTrue(isAlertPresent(), alertNotOpened);
        Assert.assertEquals(driver().switchTo().alert().getText(), "I am a JS Confirm");
        alertDismiss();
        Assert.assertEquals(verifyText(theResultContainer()), "You clicked: Cancel", "Alert not successfully dismissed");
        click(theButtonWithText("Click for JS Confirm"));
        Assert.assertTrue(isAlertPresent(), alertNotOpened);
        alertAccept();
        Assert.assertEquals(verifyText(theResultContainer()), "You clicked: Ok", "Alert not successfully accepted");

        click(theButtonWithText("Click for JS Prompt"));
        Assert.assertTrue(isAlertPresent(), alertNotOpened);
        Assert.assertEquals(driver().switchTo().alert().getText(), "I am a JS prompt");
        String promptToSend = "test";
        driver().switchTo().alert().sendKeys(promptToSend);
        alertAccept();

        Assert.assertEquals(verifyText(theResultContainer()), "You entered: " + promptToSend, "Prompt not successfully sent");
    }
}
