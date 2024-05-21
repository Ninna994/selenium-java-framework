package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import custom_framework.objects.CountryLocation;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GeolocationTest extends TheInternetFlow {
    @Test(groups = "Broken")
    @Description("Emulate geolocation using CDP")
    @Owner("Ninna994")
    public void testGeolocationChanges() {
        CountryLocation brazil = CountryLocation.CANADA;
        mockGeolocation(brazil.getLatitude(), brazil.getLongitude(), 1);
        navGeolocation();
        verifyPageTitle("Geolocation");
        click(theButtonWithText("Where am I?"));
        Assert.assertEquals(verifyText(theLatitudeValue()), String.valueOf(brazil.getLatitude()));
    }
}
