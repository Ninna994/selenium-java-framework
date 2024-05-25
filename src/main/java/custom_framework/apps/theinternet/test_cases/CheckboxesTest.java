package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class CheckboxesTest extends TheInternetFlow {
    @Test
    @Description("Check all checkboxes")
    @Owner("Ninna994")
    public void testCheckAll() {
        navCheckboxes();
        verifyPageTitle("Checkboxes");

        List<WebElement> listOfCheckboxes = driver().findElements(theCheckbox());
        for (WebElement checkbox : listOfCheckboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    @Test
    @Description("Uncheck all checkboxes")
    @Owner("Ninna994")
    public void testUncheckAll() {
        navCheckboxes();
        verifyPageTitle("Checkboxes");

        List<WebElement> listOfCheckboxes = driver().findElements(theCheckbox());
        for (WebElement checkbox : listOfCheckboxes) {
            if (checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }
}
