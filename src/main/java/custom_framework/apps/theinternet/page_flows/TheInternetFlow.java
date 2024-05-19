package custom_framework.apps.theinternet.page_flows;

import custom_framework.apps.theinternet.page_elements.TheInternetUi;
import org.testng.Assert;

public class TheInternetFlow extends TheInternetUi {
    /*
     * -------------------- Add remove elements -------------------- //
     */
    public void verifyPageTitle(String text) {
        Assert.assertEquals(verifyText(thePageTitle()), text, "Title is not right");
    }

}
