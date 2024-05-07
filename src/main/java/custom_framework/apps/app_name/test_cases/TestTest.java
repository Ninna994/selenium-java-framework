package custom_framework.apps.app_name.test_cases;

import custom_framework.apps.app_name.page_workflows.TestFlow;
import custom_framework.utils.FrameworkSetup;
import org.testng.annotations.Test;

public class TestTest extends TestFlow {
    FrameworkSetup fs = new FrameworkSetup();
    @Test
    public void testDoesItWork() {
        inputUrl(fs.getProperty("url"));
        sleepTime(5000);
    }
}
