package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlows;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddRemoveElementsTest extends TheInternetFlows {
    private static final String DELETE = "Delete";

    @Test
    @Description("Click Add Element button and Confirm that in #elements there is one button with text Delete")
    @Owner("Ninna994")
    public void testAddElement() {
        navAddRemoveElements();
        verifyPageTitle("Add/Remove Elements");

        click(theButtonWithText("Add Element"));
        waitForElementVisible(theButtonWithText(DELETE));

        Assert.assertTrue(isElementDisplayed(theButtonWithText(DELETE)), "Delete button not displayed");
        Assert.assertEquals(verifyCount(theButtonWithText(DELETE)), 1, "More/Less than 1 button present");
    }

    @Test
    @Description("Add 10 elements and delete them all")
    @Owner("Ninna994")
    public void testAddElementsAndDelete() {
        navAddRemoveElements();
        verifyPageTitle("Add/Remove Elements");

        int numberOfElements = 5;
        for (int i = 0; i < numberOfElements; i++) {
            click(theButtonWithText("Add Element"));
        }

        Assert.assertEquals(verifyCount(theButtonWithText(DELETE)), numberOfElements, "More/Less than 2 buttons present");

        while (isElementPresent(theButtonWithText(DELETE))) {
            try {
                click(theButtonWithText(DELETE));
                sleepTime(500);
            } catch (Exception e) {
                System.err.println("Error clicking delete button: " + e.getMessage());
                break;
            }
        }
        Assert.assertFalse(isElementPresent(theButtonWithText(DELETE)), "All buttons aren't deleted.");
    }

}
