package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.openqa.selenium.SearchContext;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShadowDomTest extends TheInternetFlow {
    @Test
    @Description("Access all shadowDOM elements and retrieve text")
    @Owner("Ninna994")
    public void testShadowDom() {
        navShadowDom();

        SearchContext firstHost = getShadowRoot(theShadowHost("1"));
        String firstElementText = firstHost.findElement(By.cssSelector("slot")).getText();
        Assert.assertEquals(firstElementText, "My default text", "ShadowDOM text not displayed");

        SearchContext secondHost = getShadowRoot(theShadowHost("2"));
        String secondElementText = secondHost.findElement(By.cssSelector("slot")).getText();
        Assert.assertEquals(secondElementText, "My default text", "ShadowDOM text not displayed");
    }
}
