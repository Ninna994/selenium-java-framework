package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class StatusCodesTest extends TheInternetFlow {

    @Test
    @Description("Verify status code 200 and capture response")
    @Owner("Ninna994")
    public void testPageReturned200() throws IOException {
        navStatusCodes();
        verifyPageTitle("Status Codes");
        captureResponse("/status_codes");
        clickAndWaitPageToLoad(theStatusCodesLink("200"));

        Assert.assertEquals(getUrlResponseCode("https://the-internet.herokuapp.com/status_codes"), 200, "200 status not returned");
    }

    @Test
    @Description("Verify status code 301 and capture response")
    @Owner("Ninna994")
    public void testPageReturned301() throws IOException {
        navStatusCodes();

        captureResponse("/status_codes/301");
        clickAndWaitPageToLoad(theStatusCodesLink("301"));

        Assert.assertEquals(getUrlResponseCode("https://the-internet.herokuapp.com/status_codes/301"), 301, "301 status not returned");
    }

    @Test
    @Description("Verify status code 404 and capture response")
    @Owner("Ninna994")
    public void testPageReturned404() throws IOException {
        navStatusCodes();

        captureResponse("/status_codes/404");
        clickAndWaitPageToLoad(theStatusCodesLink("404"));

        Assert.assertEquals(getUrlResponseCode("https://the-internet.herokuapp.com/status_codes/404"), 404, "404 status not returned");
    }

    @Test
    @Description("Verify status code 200 and capture response")
    @Owner("Ninna994")
    public void testPageReturned500() throws IOException {
        navStatusCodes();

        captureResponse("/status_codes/500");
        clickAndWaitPageToLoad(theStatusCodesLink("500"));

        Assert.assertEquals(getUrlResponseCode("https://the-internet.herokuapp.com/status_codes/500"), 500, "500 status not returned");
    }
}
