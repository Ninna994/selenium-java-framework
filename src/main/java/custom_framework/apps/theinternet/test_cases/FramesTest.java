package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FramesTest extends TheInternetFlow {
    @Test
    @Description("Retrieve text from nested frames")
    @Owner("Ninna994")
    public void testGetTextFromNestedIFrames() {
        navFrames();
        verifyPageTitle("Frames");

        click(theNestedFramesLink());
        waitForPageToLoad();

        focusFrame("frame-top");
        focusFrame("frame-left");
        Assert.assertEquals(verifyText(theBodyTextContainer()), "LEFT", "Top left frame not reached");

        focusFrameParent();
        focusFrame("frame-middle");
        Assert.assertEquals(verifyText(theBodyTextContainer()), "MIDDLE", "Top middle frame not reached");

        focusFrameParent();
        focusFrame("frame-right");
        Assert.assertEquals(verifyText(theBodyTextContainer()), "RIGHT", "Top right frame not reached");

        focusFrameParent();
        focusDefaultContent();
        focusFrame("frame-bottom");
        Assert.assertEquals(verifyText(theBodyTextContainer()), "BOTTOM", "Bottom frame not reached");
    }

    @Test
    @Description("Retrieve text from iframe body")
    @Owner("Ninna994")
    public void testIframeBodyText() {
        navFrames();
        click(theIFrameLink());
        waitForPageToLoad();
        focusFrame(theTinyMCEFrame());
        Assert.assertEquals(verifyText(theTinyMCEPlaceholder()), "Your content goes here.", "Iframe not reached");
    }

}
