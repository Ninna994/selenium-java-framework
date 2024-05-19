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
        clickAndWaitPageToLoad(new TheInternetUi().theAddRemoveElementsLink());
    }

    public void navBasicAuth() {
        click(new TheInternetUi().theBasicAuthLink());
    }

    public void navBrokenImages() {
        clickAndWaitPageToLoad(new TheInternetUi().theBrokenImagesLink());
    }

    public void navChallengingDOM() {
        clickAndWaitPageToLoad(new TheInternetUi().theChallengingDOMLink());
    }

    public void navCheckboxes() {
        clickAndWaitPageToLoad(new TheInternetUi().theCheckboxesLink());
    }

    public void navContextMenu() {
        clickAndWaitPageToLoad(new TheInternetUi().theContextMenuLink());
    }

    public void navDigestAuth() {
        clickAndWaitPageToLoad(new TheInternetUi().theDigestAuthLink());
    }

    public void navTheDisappearingElements() {
        clickAndWaitPageToLoad(new TheInternetUi().theDisappearingElementsLink());
    }

    public void navDragAndDrop() {
        clickAndWaitPageToLoad(new TheInternetUi().theDragAndDropLink());
    }

    public void navDropdown() {
        clickAndWaitPageToLoad(new TheInternetUi().theDropdownLink());
    }

    public void navDynamicContent() {
        clickAndWaitPageToLoad(new TheInternetUi().theDynamicContentLink());
    }

    public void navDynamicControls() {
        clickAndWaitPageToLoad(new TheInternetUi().theDynamicControlsLink());
    }

    public void navDynamicLoading() {
        clickAndWaitPageToLoad(new TheInternetUi().theDynamicLoadingLink());
    }

    public void navEntryAd() {
        clickAndWaitPageToLoad(new TheInternetUi().theEntryAdLink());
    }

    public void navExitIntent() {
        clickAndWaitPageToLoad(new TheInternetUi().theExitIntentLink());
    }

    public void navFileDownload() {
        clickAndWaitPageToLoad(new TheInternetUi().theFileDownloadLink());
    }

    public void navFileUpload() {
        clickAndWaitPageToLoad(new TheInternetUi().theFileUploadLink());
    }

    public void navFormAuthentication() {
        clickAndWaitPageToLoad(new TheInternetUi().theFormAuthenticationLink());
    }

    public void navFrames() {
        clickAndWaitPageToLoad(new TheInternetUi().theFramesLink());
    }

    public void navGeolocation() {
        clickAndWaitPageToLoad(new TheInternetUi().theGeolocationLink());
    }

    public void navHovers() {
        clickAndWaitPageToLoad(new TheInternetUi().theHoversLink());
    }

    public void navJSAlerts() {
        clickAndWaitPageToLoad(new TheInternetUi().theJSAlertsLink());
    }

    public void navShadowDom() {
        clickAndWaitPageToLoad(new TheInternetUi().theShadowDomLink());
    }

    public void navStatusCodes() {
        clickAndWaitPageToLoad(new TheInternetUi().theStatusCodesLink());
    }

}
