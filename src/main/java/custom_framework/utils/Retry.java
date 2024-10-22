package custom_framework.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.HashMap;
import java.util.Map;

public class Retry implements IRetryAnalyzer {

    private int count = 0;
    private static final int NUMBER_OF_TRIES = 1;
    private static Map<String, Integer> retryCountMap = new HashMap<>();  // To store retry counts

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {                      //Check if test not succeed
            if (count < NUMBER_OF_TRIES) {                   //Check if NUMBER_OF_TRIES count is reached
                count++;                                     //Increase the maxTry count by 1
                iTestResult.setStatus(ITestResult.FAILURE);  //Mark test as failed
                trackRetry(iTestResult);
                return true;                                 //Tells TestNG to re-run the test
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);  //If maxCount reached,test marked as failed
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
        }
        return false;
    }

    private void trackRetry(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        int retries = retryCountMap.getOrDefault(testName, 0) + 1;
        retryCountMap.put(testName, retries);
    }

    public static Map<String, Integer> getRetryCountMap() {
        return retryCountMap;
    }

}