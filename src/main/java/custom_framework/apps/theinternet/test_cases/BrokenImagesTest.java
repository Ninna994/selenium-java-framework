package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlows;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class BrokenImagesTest extends TheInternetFlows {
    @Test
    @Description("This is failing test - It asserts there are NO broken images on page")
    @Owner("Ninna994")
    public void testBrokenImagesExist() throws IOException {
        navBrokenImages();
        verifyPageTitle("Broken Images");

        List<WebElement> allImages = driver().findElements(theImageElement());
        for (WebElement image : allImages) {
            String src = image.getAttribute("src");

            int responseCode = getUrlResponseCode(src);

            System.out.println(src + "    " + responseCode);
            Assert.assertEquals(responseCode, 200, "Image at URL " + src + " is broken with response code: " + responseCode);
        }
    }

    @Test
    @Description("Verify number of images")
    @Owner("Ninna994")
    public void testImageCount() {
        navBrokenImages();
        Assert.assertEquals(verifyCount(theImageElement()), 3, "There should be 3 images present.");
    }
}
