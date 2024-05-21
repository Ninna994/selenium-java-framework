package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DynamicLoadingTest extends TheInternetFlow {
    @Test
    @Description("Test dynamic loading of elements on page")
    @Owner("Ninna994")
    public void testDynamicLoad() {
        navDynamicLoading();
        verifyPageTitle("Dynamically Loaded Page Elements");

        click(theExampleLink("1"));
        triggerLoading();

        inputUrl("https://the-internet.herokuapp.com/");
        navDynamicLoading();
        click(theExampleLink("2"));
        triggerLoading();

    }

    private void triggerLoading() {
        waitForElementVisible(theButtonWithText("Start"));
        click(theButtonWithText("Start"));
        waitForElementVisible(theLoadingBar());
        waitForElementInvisible(theLoadingBar());
        Assert.assertEquals(verifyText(theFinishMessage()), "Hello World!", "Message not displayed");
    }
}
