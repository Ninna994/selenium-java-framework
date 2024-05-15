package custom_framework.apps.theinternet.page_elements;

import custom_framework.apps.theinternet.TheInternetCommonFlows;
import org.openqa.selenium.By;

public class TheInternetUi extends TheInternetCommonFlows {
    /*
     * -------------------- COMMON ELEMENTS -------------------- //
     */
    public By thePageTitle() {
        return By.tagName("h3");
    }

    public By theButtonWithText(String text) {
        return By.xpath("//button[contains(text(), '" + text + "')]");
    }

    /*
     * -------------------- NAVIGATION LINKS -------------------- //
     */
    public By theAddRemoveElementsLink() {
        return By.href("/add_remove_elements/");
    }

    public By theBasicAuthLink() {
        return By.href("/basic_auth");
    }

    public By theBrokenImagesLink() {
        return By.href("/broken_images");
    }



}
