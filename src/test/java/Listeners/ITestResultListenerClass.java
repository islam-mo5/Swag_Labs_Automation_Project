package Listeners;

import Utilities.LogsUtilities;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class ITestResultListenerClass implements ITestListener {

    public void onTestStart(ITestResult result) {
        LogsUtilities.info("Test Case " + result.getName() + " started");
    }

    public void onTestSuccess(ITestResult result) {
        LogsUtilities.info("Test Case " + result.getName() + " passed");
    }

    public void onTestSkipped(ITestResult result) {
        LogsUtilities.info("Test Case " + result.getName() + " skipped");
    }
}
