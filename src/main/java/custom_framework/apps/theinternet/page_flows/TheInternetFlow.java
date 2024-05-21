package custom_framework.apps.theinternet.page_flows;

import custom_framework.apps.theinternet.page_elements.TheInternetUi;
import org.testng.Assert;

public class TheInternetFlow extends TheInternetUi {
    public void verifyPageTitle(String text) {
        Assert.assertEquals(verifyText(thePageTitle()), text, "Title is not right");
    }

    public void verifyPageTitleH4(String text) {
        Assert.assertEquals(verifyText(thePageTitleH4()), text, "Title is not right");
    }

}
