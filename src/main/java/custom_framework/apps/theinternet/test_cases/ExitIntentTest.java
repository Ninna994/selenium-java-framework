package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExitIntentTest extends TheInternetFlow {
    @Test
    @Description("Navigate outside the viewport to trigger exit intent modal window")
    @Owner("Ninna994")
    public void testExitIntentHandling() {
        navExitIntent();
        verifyPageTitle("Exit Intent");

        navigateOutsideViewport();

        waitForElementVisible(theModal());
        Assert.assertTrue(isElementDisplayed(theModal()), "Modal not displayed");
        Assert.assertEquals(verifyText(theModalTitle()), "THIS IS A MODAL WINDOW", "Modal title does not match");
        Assert.assertEquals(verifyText(theModalBody()), "It's commonly used to encourage a user to take an action (e.g., give their e-mail address to sign up for something).", "Modal body text does not match");
        click(theModalCloseButton());
        waitForElementInvisible(theModal());
    }
}
