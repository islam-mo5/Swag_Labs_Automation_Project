package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Utilities.DataUtilities;
import Utilities.LogsUtilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtilities.getPropertyValue;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC01_LoginPage {

    private final String USERNAME = DataUtilities.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtilities.getJsonData("validLogin", "password");

    @BeforeMethod
    public void setup() throws IOException {
        //ternary operation
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtilities.getPropertyValue("environment", "BROWSER");
        LogsUtilities.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtilities.info("Browser driver is opened");
        getDriver().get(DataUtilities.getPropertyValue("environment", "BASE_URL"));
        LogsUtilities.info("Page directed to Base URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void validLoginTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTC(getPropertyValue("environment", "HOME_URL")));
    }

    @AfterMethod
    public void quit() {
        removeDriver();
    }

}
