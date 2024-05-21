package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class HoversTest extends TheInternetFlow {
    @Test
    @Description("Assert information appear when user hovers over images")
    @Owner("Ninna994")
    public void testHoverInformationAppears() {
        navHovers();
        verifyPageTitle("Hovers");
        List<WebElement> imageContainers = driver().findElements(theContentImages());
        int increment = 1;
        for (WebElement image : imageContainers) {
            hoverOver(image);
            sleepTime(1000);
            String expectedUserName = "name: user" + increment;
            String expectedLink = "https://the-internet.herokuapp.com/users/" + increment;

            Assert.assertEquals(verifyText(theNameContainer(String.valueOf(increment))), expectedUserName);
            Assert.assertEquals(getAttribute(theUsersLinkContainer(String.valueOf(increment)), "href"), expectedLink);
            increment++;
        }
    }
}
