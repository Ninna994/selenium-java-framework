package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class DropdownTest extends TheInternetFlow {
    @Test
    @Description("List all options")
    @Owner("Ninna994")
    public void testListAllOptions() {
        navDropdown();
        verifyPageTitle("Dropdown List");
        List<WebElement> allOptions = getOptions(theDropdown());
        for (WebElement option : allOptions) {
            System.out.println(option.getText());
        }
        Assert.assertFalse(allOptions.isEmpty(), "List of options is empty");
    }

    @Test
    @Description("Select option by value, by index and By visible text")
    @Owner("Ninna994")
    public void testSelectByValue() {
        navDropdown();
        select(theDropdown(), "Option 1");
        selectByValue(theDropdown(), "2");
        selectByIndex(theDropdown(), 1);
    }

}
