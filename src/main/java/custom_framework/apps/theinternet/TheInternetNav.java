package custom_framework.apps.theinternet;

import custom_framework.apps.theinternet.page_elements.TheInternetUi;
import custom_framework.utils.FrameworkSetup;
import custom_framework.utils.SharedMethods;
import org.testng.annotations.BeforeMethod;

public class TheInternetNav extends SharedMethods {
    @BeforeMethod
    public void visitTheInternet() {
        FrameworkSetup fs = new FrameworkSetup();
        inputUrl(fs.getProperty("url"));
    }

    public void navAddRemoveElements() {
        visitTheInternet();
        clickAndWaitPageToLoad(new TheInternetUi().theAddRemoveElementsLink());
    }
}
