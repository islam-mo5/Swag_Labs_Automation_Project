package Tests;


import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Utilities.DataUtilities;
import Utilities.LogsUtilities;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactory.*;
import static Utilities.Utility.*;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC02_HomePageTest {
    private final String USERNAME = DataUtilities.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtilities.getJsonData("validLogin", "password");
    private Set<Cookie> cookies;

    @BeforeClass
    public void login() throws IOException {
        //ternary operation
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtilities.getPropertyValue("environment", "BROWSER");
        LogsUtilities.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtilities.info("Browser driver is opened");
        getDriver().get(DataUtilities.getPropertyValue("environment", "BASE_URL"));
        LogsUtilities.info("Page directed to Base URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin();
        cookies = getAllCookies(getDriver());
        removeDriver();
    }

    @BeforeMethod
    public void setup() throws IOException {
        //ternary operation
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtilities.getPropertyValue("environment", "BROWSER");
        LogsUtilities.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtilities.info("Browser driver is opened");
        getDriver().get(DataUtilities.getPropertyValue("environment", "BASE_URL"));
        LogsUtilities.info("Page directed to Base URL");
        restoreSession(getDriver(), cookies);
        getDriver().get(DataUtilities.getPropertyValue("environment", "HOME_URL"));
        LogsUtilities.info("Page directed to Home URL");
        getDriver().navigate().refresh();
    }

    @Test
    public void verifyNumberOfSelectedProductsTC() {
        new P02_HomePage(getDriver()).addProductsToCart();
        Assert.assertTrue(new P02_HomePage(getDriver()).compareNumberOfSelectedProductWithCart());
    }

    @Test
    public void addRandomProductsToCartTC() {

        new P02_HomePage(getDriver()).addRandomProducts(4, 6);
        Assert.assertTrue(new P02_HomePage(getDriver()).compareNumberOfSelectedProductWithCart());
    }

    @Test
    public void clickOnCartTC() throws IOException {
        new P02_HomePage(getDriver()).clickOnCart();
        Assert.assertTrue(verifyUrl(getDriver(), DataUtilities.getPropertyValue("environment", "CART_URL")));
    }

    @AfterMethod
    public void quit() {
        removeDriver();
    }

    @AfterClass
    public void deleteSession() {
        cookies.clear();
    }


}
