package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FileUploadTest extends TheInternetFlow {
    @Test
    @Description("Upload file using sendKeys method")
    @Owner("Ninna994")
    public void testUploadSendKeysMethod() {
        navFileUpload();
        verifyPageTitle("File Uploader");

        String fileName = "automation.jpg";
        uploadFile(fileName);
        click(theFileUploadButton(), 500);
        waitForPageToLoad();
        verifyPageTitle("File Uploaded!");
        Assert.assertEquals(verifyText(theUploadedFiledContainer()), fileName, "File name does not match");
    }

    @Test(groups = "Ignore")
    @Description("Upload file using JS Executor to set value of input element if inaccessible. This test will not work in our case because element is actually accessible.")
    @Owner("Ninna994")
    public void testUploadJSExecutor() {
        navFileUpload();

        String fileName = "automation.jpg";
        uploadFileJS(fileName);

        click(theFileUploadButton(), 500);
        waitForPageToLoad();
        verifyPageTitle("File Uploaded!");
        Assert.assertEquals(verifyText(theUploadedFiledContainer()), fileName, "File name does not match");
    }

}
