package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DynamicControlsTest extends TheInternetFlow {
    @Test
    @Description("Verify that checkboxes can be added and removed")
    @Owner("Ninna994")
    public void testCheckboxRemoveAddFunctionality() {
        navDynamicControls();
        verifyPageTitleH4("Dynamic Controls");

        Assert.assertTrue(isElementDisplayed(theCheckbox()), "Checkbox not displayed");
        click(theButtonWithText("Remove"));
        waitForElementVisible(theMessage());
        Assert.assertEquals(verifyText(theMessage()), "It's gone!", "Message text for checkbox removal does not match");

        int checkboxCount = driver().findElements(theCheckbox()).size();
        Assert.assertEquals(checkboxCount, 0, "Checkbox should be removed");

        click(theButtonWithText("Add"));
        waitForElementVisible(theMessage());
        Assert.assertEquals(verifyText(theMessage()), "It's back!", "Message text for adding checkbox does not match");
        Assert.assertTrue(isElementDisplayed(theCheckbox()), "Checkbox not added");
        checkboxCount = driver().findElements(theCheckbox()).size();
        Assert.assertEquals(checkboxCount, 1, "Checkbox count should be 1");
    }

    @Test
    @Description("Test that input can be enabled and disabled")
    @Owner("Ninna994")
    public void testInputEnableDisableFunctionality() {
        navDynamicControls();
        Assert.assertFalse(isElementEnabled(theTextInput()), "Input should be disabled.");
        click(theButtonWithText("Enable"));

        waitForElementVisible(theMessage());
        Assert.assertEquals(verifyText(theMessage()), "It's enabled!", "Enabled text not displayed");
        Assert.assertTrue(isElementEnabled(theTextInput()), "Element not enabled");
        input(theTextInput(), "Test text");

        click(theButtonWithText("Disable"));
        waitForElementVisible(theMessage());
        Assert.assertEquals(verifyText(theMessage()), "It's disabled!", "Disabled text not displayed");
        Assert.assertFalse(isElementEnabled(theTextInput()), "Input should be disabled.");
    }
}
