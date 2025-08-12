package E2E;

import Pages.P01_LoginPage;
import Utilities.DataUtilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

public class E2E02_InvalidLoginTest {
    private final String USERNAME = DataUtilities.getJsonData("validLogin", "username");
    private final String INVALID_PASSWORD = "wrongPassword";

    @BeforeMethod
    public void setup() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtilities.getPropertyValue("environment", "BROWSER");
        setupDriver(browser);
        getDriver().get(DataUtilities.getPropertyValue("environment", "BASE_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void invalidLoginTest() throws IOException {
        P01_LoginPage loginPage = new P01_LoginPage(getDriver());
        loginPage.enterUsername(USERNAME).enterPassword(INVALID_PASSWORD).clickOnLogin();
        Assert.assertFalse(loginPage.assertLoginTC(DataUtilities.getPropertyValue("environment", "HOME_URL")), "User should not be logged in!");
    }

    @AfterMethod
    public void quit() {
        removeDriver();
    }
}
