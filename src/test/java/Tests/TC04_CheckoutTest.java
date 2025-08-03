package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Utilities.DataUtilities;
import Utilities.LogsUtilities;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC04_CheckoutTest {

    private final String USERNAME = DataUtilities.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtilities.getJsonData("validLogin", "password");
    private final String FIRSTNAME = DataUtilities.getJsonData("checkoutInfo", "fName") + " - " + Utility.getTimeStamp();
    private final String LASTNAME = DataUtilities.getJsonData("checkoutInfo", "lName") + " - " + Utility.getTimeStamp();
    private final String POSTALCODE = new Faker().number().digits(5);

    @BeforeMethod
    public void setup() throws IOException {
        //ternary operation
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtilities.getPropertyValue("environment", "BROWSER");
        LogsUtilities.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtilities.info("Page directed to Base URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void checkoutStepOneTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(4, 6)
                .clickOnCart()
                .clickOnCheckoutButton()
                .addInfo(FIRSTNAME, LASTNAME, POSTALCODE)
                .clickOnContinueButton();
        LogsUtilities.info(FIRSTNAME + " " + LASTNAME + "  " + POSTALCODE);
        Assert.assertTrue(Utility.verifyUrl(getDriver(), DataUtilities.getPropertyValue("environment", "OVERVIEW_URL")));
    }

    @AfterMethod
    public void quit() {
        removeDriver();
    }

}

