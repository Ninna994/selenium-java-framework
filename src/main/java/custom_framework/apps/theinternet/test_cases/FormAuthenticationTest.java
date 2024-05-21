package custom_framework.apps.theinternet.test_cases;

import custom_framework.apps.theinternet.page_flows.TheInternetFlow;
import custom_framework.utils.FrameworkSetup;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FormAuthenticationTest extends TheInternetFlow {
    @Test
    @Description("Provide set of check for form validations")
    @Owner("Ninna994")
    public void testFormAuthentication() {
        String wrongText = "Test";
        String usernameValidation = "Your username is invalid!";
        String assertMessage = "Wrong validation present";

        FrameworkSetup fs = new FrameworkSetup();
        navFormAuthentication();
        // Submit empty form
        click(theSubmitButton(), 500);
        Assert.assertTrue(isElementDisplayed(theAlertMessageContainer()));
        Assert.assertTrue(verifyText(theAlertMessageContainer()).contains(usernameValidation), assertMessage);

        //enter just password but wrong
        input(thePasswordField(), wrongText);
        click(theSubmitButton());
        Assert.assertTrue(isElementDisplayed(theAlertMessageContainer()));
        Assert.assertTrue(verifyText(theAlertMessageContainer()).contains(usernameValidation), assertMessage);

        //enter only username, but wrong
        input(theUsernameField(), wrongText);
        click(theSubmitButton());
        Assert.assertTrue(isElementDisplayed(theAlertMessageContainer()));
        Assert.assertTrue(verifyText(theAlertMessageContainer()).contains(usernameValidation), assertMessage);

        //enter both username and password wrong
        input(theUsernameField(), wrongText);
        input(thePasswordField(), wrongText);
        click(theSubmitButton());
        Assert.assertTrue(isElementDisplayed(theAlertMessageContainer()));
        Assert.assertTrue(verifyText(theAlertMessageContainer()).contains(usernameValidation), assertMessage);

        //enter username right, password wrong
        input(theUsernameField(), fs.getProperty("loginUsername"));
        input(thePasswordField(), wrongText);
        click(theSubmitButton());
        Assert.assertTrue(isElementDisplayed(theAlertMessageContainer()));
        Assert.assertTrue(verifyText(theAlertMessageContainer()).contains("Your password is invalid!"), assertMessage);

        // enter username wrong, password right
        input(theUsernameField(), wrongText);
        input(thePasswordField(), fs.getProperty("loginPassword"));
        click(theSubmitButton());
        Assert.assertTrue(isElementDisplayed(theAlertMessageContainer()));
        Assert.assertTrue(verifyText(theAlertMessageContainer()).contains(usernameValidation), assertMessage);

        //enter both fields correctly
        input(theUsernameField(), fs.getProperty("loginUsername"));
        input(thePasswordField(), fs.getProperty("loginPassword"));
        click(theSubmitButton());

        waitForPageToLoad();
        Assert.assertTrue(isElementDisplayed(theSuccessAlert()), "Success alert not displayed");
        Assert.assertEquals(verifyText(theSubHeader()), "Welcome to the Secure Area. When you are done click logout below.", "Wrong subheader message displayed");

        //logout user
        click(theLogoutButton());
        waitForPageToLoad();
        Assert.assertTrue(isElementDisplayed(theSuccessAlert()), "Success alert not displayed");
        Assert.assertTrue(verifyText(theSuccessAlert()).contains("You logged out of the secure area!"), "Wrong sub-header message displayed");


    }
}
