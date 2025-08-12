package E2E;

import Pages.P01_LoginPage;
import Utilities.DataUtilities;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

public class E2E05_CheckoutMissingInfoTest {

    private final String USERNAME = DataUtilities.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtilities.getJsonData("validLogin", "password");
    private final String FIRSTNAME = ""; // Missing first name
    private final String LASTNAME = DataUtilities.getJsonData("checkoutInfo", "lName") + Utility.getTimeStamp();
    private final String POSTALCODE = new Faker().number().digits(5);

    @BeforeMethod
    public void setup() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtilities.getPropertyValue("environment", "BROWSER");
        setupDriver(browser);
        getDriver().get(DataUtilities.getPropertyValue("environment", "BASE_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void checkoutWithMissingFirstName() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(2, 6)
                .clickOnCart()
                .clickOnCheckoutButton()
                .addInfo(FIRSTNAME, LASTNAME, POSTALCODE)
                .clickOnContinueButton();
        // Assert error message is displayed (locator and assertion depend on your implementation)
        Assert.assertTrue(Utility.getText(getDriver(), org.openqa.selenium.By.cssSelector("[data-test='error']")).contains("First Name is required"));
    }

    @AfterMethod
    public void quit() {
        removeDriver();
    }
}