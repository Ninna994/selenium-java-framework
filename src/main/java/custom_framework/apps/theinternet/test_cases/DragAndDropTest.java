package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DragAndDropTest extends TheInternetFlow {
    @Test
    @Description("Play with drag and drop functionality")
    @Owner("Ninna994")
    public void testDragAndDrop() {
        navDragAndDrop();
        verifyPageTitle("Drag and Drop");

        dragAndDrop(theADragElement(), theBDragElement());

        String firstColumnText = driver().findElement(theADragElement()).findElement(theHeaderElement()).getText();
        String secondColumnText = driver().findElement(theBDragElement()).findElement(theHeaderElement()).getText();

        Assert.assertEquals(firstColumnText + secondColumnText, "BA");

        sleepTime(2000);

        dragAndDrop(theADragElement(), theBDragElement());
        String firstColumnTextNew = driver().findElement(theADragElement()).findElement(theHeaderElement()).getText();
        String secondColumnTextNew = driver().findElement(theBDragElement()).findElement(theHeaderElement()).getText();
        Assert.assertEquals(firstColumnTextNew + secondColumnTextNew, "AB");
    }
}
