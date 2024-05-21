package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EntryAdTest extends TheInternetFlow {
    @Test
    @Description("Handle one time entry ad, close it, clear storage and cache and refresh page to display it again.")
    @Owner("Ninna994")
    public void testEntryAdHandling() {
        navEntryAd();
        Assert.assertTrue(isElementDisplayed(theModal()), "Modal not displayed");
        Assert.assertEquals(verifyText(theModalTitle()), "THIS IS A MODAL WINDOW", "Modal title does not match");
        Assert.assertEquals(verifyText(theModalBody()), "It's commonly used to encourage a user to take an action (e.g., give their e-mail address to sign up for something or disable their ad blocker).", "Modal body text does not match");
        click(theModalCloseButton());
        waitForElementInvisible(theModal());

        refreshPage();
        Assert.assertFalse(isElementDisplayed(theModal()), "Modal appeared but shouldn't have");
        refreshPage();
        Assert.assertFalse(isElementDisplayed(theModal()), "Modal appeared but shouldn't have");

        clearCache();
        clearStorage("https://the-internet.herokuapp.com/");

        refreshPage();
        waitForElementVisible(theModal());
        Assert.assertTrue(isElementDisplayed(theModal()), "Modal not displayed");
    }
}
