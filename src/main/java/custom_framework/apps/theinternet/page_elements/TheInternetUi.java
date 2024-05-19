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

    public By theImageElement() {
        return By.tagName("img");
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

    public By theChallengingDOMLink() {
        return By.href("/challenging_dom");
    }

    public By theCheckboxesLink() {
        return By.href("/checkboxes");
    }

    public By theContextMenuLink() {
        return By.href("/context_menu");
    }

    public By theDigestAuthLink() {
        return By.href("/digest_auth");
    }


    /*
     * -------------------- THE CHALLENGING DOM -------------------- //
     */

    public By theElementInTable(int row, int column) {
        return By.xpath("//table//tbody//tr[" + row + "]//td[" + column + "]");
    }

    public By theFirstButton() {
        return By.xpath("(//div//a)[2]");
    }

    public By theCanvas() {
        return By.id("canvas");
    }

    /*
     * -------------------- CHECKBOXES -------------------- //
     */

    public By theCheckbox() {
        return By.type("checkbox");
    }

    /*
     * -------------------- CONTEXT MENU -------------------- //
     */

    public By theHotspotContainer() {
        return By.id("hot-spot");
    }
}
