package E2E;

import Pages.*;
import Utilities.DataUtilities;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

public class E2E04_ProductFilteringAndPurchaseTest {

    private final String USERNAME = DataUtilities.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtilities.getJsonData("validLogin", "password");
    private final String FIRSTNAME = DataUtilities.getJsonData("checkoutInfo", "fName") + Utility.getTimeStamp();
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
    public void productFilteringAndPurchase() throws IOException {
        // Login
        P02_HomePage homePage = new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin();

        // Filter products by "Price (low to high)" (assuming a dropdown with id "product_sort_container")
        By sortDropdown = By.className("product_sort_container");
        Utility.selectFromDropdown(getDriver(), sortDropdown, "Price (low to high)");

        // Add the first product after filtering
        By firstAddToCart = By.xpath("(//button[contains(@class,'btn_inventory')])[1]");
        Utility.clickElement(getDriver(), firstAddToCart);

        // Assert cart icon shows 1 product
        String cartCount = Utility.getText(getDriver(), By.cssSelector(".shopping_cart_badge"));
        Assert.assertEquals(cartCount, "1");

        // Continue to checkout
        P03_CartPage cartPage = homePage.clickOnCart();
        P04_CheckoutPage checkoutPage = cartPage.clickOnCheckoutButton();
        checkoutPage.addInfo(FIRSTNAME, LASTNAME, POSTALCODE)
                .clickOnContinueButton();

        // Overview page: verify prices
        P05_OverviewPage overviewPage = new P05_OverviewPage(getDriver());
        Assert.assertTrue(overviewPage.compareFinalPrices());

        // Finish order
        overviewPage.clickOnFinishButton();
        Assert.assertTrue(new P06_CompletePage(getDriver()).checkShownThankYouMessage());
    }

    @AfterMethod
    public void quit() {
        removeDriver();
    }
}