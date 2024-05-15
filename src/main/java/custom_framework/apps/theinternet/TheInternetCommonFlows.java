package custom_framework.apps.theinternet;

import custom_framework.apps.theinternet.page_flows.TheInternetFlows;

public class TheInternetCommonFlows extends TheInternetNav {
    public void goVerifyTitle(String text) {
        new TheInternetFlows().verifyPageTitle(text);
    }
}
