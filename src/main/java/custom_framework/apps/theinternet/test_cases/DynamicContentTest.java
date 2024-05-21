package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class DynamicContentTest extends TheInternetFlow {
    @Test
    @Description("Verify dynamic content changes")
    @Owner("Ninna994")
    public void testDynamicContentChanges() {
        navDynamicContent();
        verifyPageTitle("Dynamic Content");

        List<String> initialContent = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            String imageSource = driver().findElements(theImageContainer()).get(i).getAttribute("src");
            String textContent = driver().findElements(theTextContainer()).get(i).getText();
            initialContent.add(imageSource + textContent);
        }

        refreshPage();

        List<String> newContent = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            String imageSource = driver().findElements(theImageContainer()).get(i).getAttribute("src");
            String textContent = driver().findElements(theTextContainer()).get(i).getText();
            newContent.add(imageSource + textContent);
        }

        boolean contentChanged = !initialContent.equals(newContent);
        Assert.assertTrue(contentChanged, "Content did not change after reload");

    }

    @Test
    public void testVerifyDynamicChangesAfterMultiplePageRefresh() {
        navDynamicContent();

        List<String> previousContent = null;
        for (int reloadCount = 0; reloadCount < 5; reloadCount++) {
            List<String> currentContent = new ArrayList<>();

            for (int i = 1; i <= 2; i++) {
                String imageSource = driver().findElements(theImageContainer()).get(i).getAttribute("src");
                String textContent = driver().findElements(theTextContainer()).get(i).getText();
                currentContent.add(imageSource + textContent);
            }

            if (previousContent != null) {
                boolean contentChanged = !previousContent.equals(currentContent);
                Assert.assertTrue(contentChanged, "Content did not change after reload #" + (reloadCount + 1) + ".");
            }

            previousContent = currentContent;
            refreshPage();
        }
    }
}
