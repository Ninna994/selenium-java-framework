package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlows;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TheAddRemoveElementsTest extends TheInternetFlows {
    private static final String DELETE = "Delete";

    @Test
    public void testAddElement() {
        // Click Add Element button and Confirm that in #elements there is one button with text Delete
        navAddRemoveElements();
        Assert.assertEquals(verifyText(thePageTitle()), "Add/Remove Elements", "Title is not right");

        click(theButtonWithText("Add Element"));
        waitForElementVisible(theButtonWithText(DELETE));

        Assert.assertTrue(isElementDisplayed(theButtonWithText(DELETE)), "Delete button not displayed");
        Assert.assertEquals(verifyCount(theButtonWithText(DELETE)), 1, "More/Less than 1 button present");
    }

    @Test
    public void testAddElementsAndDelete() {
        // Add 10 elements and delete them all
        navAddRemoveElements();
        Assert.assertEquals(verifyText(thePageTitle()), "Add/Remove Elements", "Title is not right");

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
