package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DisappearingElementsTest extends TheInternetFlow {
    @Test
    @Description("Count elements and verify Gallery is not displayed after refresh")
    @Owner("Ninna994")
    public void testGalleryAppearanceAfterRefresh() {
        navTheDisappearingElements();
        verifyPageTitle("Disappearing Elements");
        int initialCount = verifyCount(theNavElements());
        refreshPage();
        refreshPage(); //current bug on website, need to perform two refreshes in order to change count
        sleepTime(2000);
        waitForPageToLoad();
        int countAfterRefresh = verifyCount(theNavElements());

        Assert.assertTrue(countAfterRefresh != initialCount,  "Count does not match");

    }
}
