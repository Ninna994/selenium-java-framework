package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import custom_framework.utils.FrameworkSetup;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v124.network.Network;
import org.openqa.selenium.devtools.v124.network.model.Headers;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BasicAuthTest extends TheInternetFlow {

    public static final String USERNAME_PASS = "admin";

    @Test
    @Description("Handling Basic Auth with Authentication URL")
    @Owner("Ninna994")
    public void testLoginUserAuthenticationUrlMethod() {
        String baseUrl = new FrameworkSetup().getProperty("url") + "/basic_auth";
        String authUrl = "https://" + USERNAME_PASS + ":" + USERNAME_PASS + "@" + baseUrl.replace("https://", "");
        inputUrl(authUrl);

        sleepTime(2000);
        goVerifyTitle("Basic Auth");
        Assert.assertEquals(verifyText(By.tagName("p")), "Congratulations! You must have the proper credentials.", "Wrong text displayed");
    }

    @Test
    @Description("Handling Basic Auth with CDP")
    @Owner("Ninna994")
    public void testLoginUserCDPMethod() {
        FrameworkSetup fs = new FrameworkSetup();
        DevTools devTools = null;
        if (fs.getBrowser().equalsIgnoreCase("chrome")) {
            devTools = ((ChromeDriver) driver()).getDevTools();
        } else if (fs.getBrowser().equalsIgnoreCase("edge")) {
            devTools = ((EdgeDriver) driver()).getDevTools();
        }

        assert devTools != null;
        devTools.createSession();

        devTools.send(Network.enable(Optional.of(0), Optional.of(0), Optional.of(0)));
        Map<String, Object> header = new HashMap<>();
        String basicAuth = "Basic " + new String(new Base64().encode(String.format("%s:%s", USERNAME_PASS, USERNAME_PASS).getBytes()));

        header.put("Authorization", basicAuth);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(header)));
        navBasicAuth();
        sleepTime(2000);

        goVerifyTitle("Basic Auth");
        Assert.assertEquals(verifyText(By.tagName("p")), "Congratulations! You must have the proper credentials.", "Wrong text displayed");
    }

}
