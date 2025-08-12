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

public class E2E03_PurchaseWithCartModificationTest {

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
    public void purchaseWithCartModification() throws IOException {
        // Login and add products
        P02_HomePage homePage = new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addProductsToCart();

        // Go to cart
        P03_CartPage cartPage = homePage.clickOnCart();

        // Remove the first product (simulate by clicking the first REMOVE button)
        By firstRemoveButton = By.xpath("(//button[text()='REMOVE'])[1]");
        Utility.clickElement(getDriver(), firstRemoveButton);

        // Assert that the number of products decreased
        int productsAfterRemove = getDriver().findElements(By.xpath("//button[text()='REMOVE']")).size();
        Assert.assertTrue(productsAfterRemove < 6);

        // Continue checkout
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