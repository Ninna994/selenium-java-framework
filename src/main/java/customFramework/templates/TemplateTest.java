package customFramework.templates;

public class TemplateTest {
    /*
     * Test methods should appear in the exact same order as the workflows of the respective Flow class.
     * Test methods should be organized in alphabetical order
     * 1:1 ratio between workflows and test methods
     *
     * Test methods are unique in that:
     * -- They use GIVEN, WHEN, THEN
     * -- They use Asserts (assert that something is on the page, that something is NOT on the page, that expected value is correctly displayed)
     *
     * Local variables that are reused in multiple test classes should be added to the GlobalVariables utils class.
     * GlobalVariables need to be instantiated in the test method so that a new value will be created for that test method.
     * Likewise, when referencing flows from outside the extended workflow class, you'll need to instantiate the workflow with "new"
     *
     * - Test cases use Asserts
    - Assert that something is on the page
        - ``` Use Assert.assertTrue(isElementPresent(UiClass.ByElement), "String message"); ```
    - Assert that something is NOT on the page
        - ```Assert.assertFalse(isElementPresent(By element), "String message")```
    - Assert that expected value is correctly displayed
        - ```Assert.assertEquals((PageClass.verifyMethod), "String value");```
- Groups and description
    - **@groups** are used to group test cases
    - **@description** is usually Jira ticket link
     */
}