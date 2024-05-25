package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class FileDownloadTest extends TheInternetFlow {

    @Test
    @Description("Count all .jpeg files, download them and delete afterwards")
    @Owner("Ninna994")
    public void testDownloadAllExtensionAndDeleteThemAfterwards() {
        navFileDownload();
        int startingCount = getFileCount();
        System.out.println("Starting file count is: " + startingCount);
        List<WebElement> allFiles = driver().findElements(theDownloadLinks());
        int extensionFileCount = 0;
        for (WebElement file : allFiles) {
            if (file.getText().endsWith(".jpeg")) { //change extension as you wish
                System.out.println(file.getText());
                file.click();
                sleepTime(1000);
                System.out.println("Downloaded file " + findLastDownloadedFile());
                extensionFileCount++;
            }
        }
        sleepTime(1000);
        int endingCount = getFileCount();
        System.out.println("Ending file count is: " + endingCount);
        assertEquals(endingCount - startingCount, extensionFileCount, "File count does not match");

        deleteAllFilesFromDownloadDirectory();
        int fileCountAfterDeletion = getFileCount();
        assertEquals(fileCountAfterDeletion, 0, "Not all files were deleted");
    }

    @Test
    @Description("Download single file")
    @Owner("Ninna994")
    public void testDownloadFile() {
        navFileDownload();
        verifyPageTitle("File Downloader");
        int startingFileCount = getFileCount();
        click(theDownloadLinks(), 2000);
        String downloadedFileName = findLastDownloadedFile();
        System.out.println(downloadedFileName);
        int endCount = getFileCount();
        assertEquals(endCount - startingFileCount, 1, "File not downloaded");
    }

    @Test
    @Description("Download single file and delete files from download folder afterwards")
    @Owner("Ninna994")
    public void testDownloadFileAndDeleteDownloadsFolderContents() {
        navFileDownload();
        verifyPageTitle("File Downloader");

        int startingFileCount = getFileCount();

        click(theDownloadLinks(), 2000);
        int endCount = getFileCount();
        assertEquals(endCount - startingFileCount, 1, "File not downloaded");

        deleteAllFilesFromDownloadDirectory();
        int fileCountAfterDeletion = getFileCount();
        assertEquals(fileCountAfterDeletion, 0, "Not all files were deleted");
    }

}
