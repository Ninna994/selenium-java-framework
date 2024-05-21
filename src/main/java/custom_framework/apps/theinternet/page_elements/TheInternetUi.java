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

    public By thePageTitleH4() {
        return By.tagName("h4");
    }

    public By theImageElement() {
        return By.tagName("img");
    }

    public By theButtonWithText(String text) {
        return By.xpath("//button[contains(text(), '" + text + "')]");
    }

    public By theCheckbox() {
        return By.type("checkbox");
    }

    public By theDropdown() {
        return By.id("dropdown");
    }

    public By theMessage() {
        return By.id("message");
    }

    public By theTextInput() {
        return By.cssSelector("input[type='text']");
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

    public By theDisappearingElementsLink() {
        return By.href("/disappearing_elements");
    }

    public By theDragAndDropLink() {
        return By.href("/drag_and_drop");
    }

    public By theDropdownLink() {
        return By.href("/dropdown");
    }

    public By theDynamicContentLink() {
        return By.href("/dynamic_content");
    }

    public By theDynamicControlsLink() {
        return By.href("/dynamic_controls");
    }

    public By theDynamicLoadingLink() {
        return By.href("/dynamic_loading");
    }

    public By theEntryAdLink() {
        return By.href("/entry_ad");
    }

    public By theExitIntentLink() {
        return By.href("/exit_intent");
    }

    public By theFileDownloadLink() {
        return By.href("/file_download");
    }

    public By theFileUploadLink() {
        return By.href("/file_upload");
    }

    public By theFormAuthenticationLink() {
        return By.href("/login");
    }

    public By theFramesLink() {
        return By.href("/frames");
    }

    public By theGeolocationLink() {
        return By.href("/geolocation");
    }

    public By theHoversLink() {
        return By.href("/hovers");
    }

    public By theJSAlertsLink() {
        return By.href("/javascript_alerts");
    }

    public By theShadowDomLink() {
        return By.href("/shadowdom");
    }

    public By theStatusCodesLink() {
        return By.href("/status_codes");
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
     * -------------------- CONTEXT MENU -------------------- //
     */

    public By theHotspotContainer() {
        return By.id("hot-spot");
    }

    /*
     * -------------------- DISAPPEARING ELEMENTS -------------------- //
     */

    public By theNavElements() {
        return By.cssSelector("li a");
    }

    /*
     * -------------------- DRAG AND DROP -------------------- //
     */

    public By theADragElement() {
        return By.id("column-a");
    }

    public By theBDragElement() {
        return By.id("column-b");
    }

    public By theHeaderElement() {
        return By.tagName("header");
    }

    /*
     * -------------------- DYNAMIC CONTENT -------------------- //
     */
    public By theImageContainer() {
        return By.cssSelector(".large-2 img");
    }

    public By theTextContainer() {
        return By.className("large-10");
    }

    /*
     * -------------------- DYNAMIC LOADING -------------------- //
     */

    public By theExampleLink(String elementNumber) {
        return By.cssSelector("a[href='/dynamic_loading/" + elementNumber + "']");
    }

    public By theLoadingBar() {
        return By.id("loading");
    }

    public By theFinishMessage() {
        return By.cssSelector("#finish h4");
    }

    /*
     * -------------------- ENTRY AD -------------------- //
     */

    public By theModal() {
        return By.className("modal");
    }

    public By theModalTitle() {
        return By.cssSelector(".modal-title h3");
    }

    public By theModalBody() {
        return By.cssSelector(".modal-body p");
    }

    public By theModalCloseButton() {
        return By.cssSelector(".modal-footer p");
    }

    public By theRestartAdButton() {
        return By.id("restart-ad");
    }

    /*
     * -------------------- EXIT INTENT  -------------------- //
     */
    public By theRowTopElement() {
        return By.className("row");
    }
    /*
     * --------------------  -------------------- //
     */

    /*
     * --------------------  -------------------- //
     */

    /*
     * --------------------  -------------------- //
     */


}
