package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_elements.TheInternetUi;
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

        Assert.assertEquals(verifyCount(new TheInternetUi().theNavElements()), 5, "More/less than 5 nav elements present before refreshing page.");

        refreshPage();
        sleepTime(2000);
        waitForPageToLoad();
        Assert.assertEquals(verifyCount(new TheInternetUi().theNavElements()), 4, "More/less than 4 nav elements present after refreshing page.");
        String lastElementText = getLastElement(theNavElements()).getText();
        Assert.assertEquals(lastElementText, "Portfolio", "Portfolio is not last element");
    }
}
