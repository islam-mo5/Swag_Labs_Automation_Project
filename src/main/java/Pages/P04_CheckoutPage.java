package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static Utilities.Utility.generalWait;

public class P04_CheckoutPage {
    private final WebDriver driver;

    private final By firstNameLocator = By.id("first-name");
    private final By lastNameLocator = By.id("last-name");
    private final By postalCodeLocator = By.id("postal-code");
    private final By continueButtonLocator = By.cssSelector(".cart_button");

    public P04_CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }


    public P04_CheckoutPage addInfo(String fName, String lName, String postalCode) {
        Utility.sendData(driver, firstNameLocator, fName);
        Utility.sendData(driver, lastNameLocator, lName);
        Utility.sendData(driver, postalCodeLocator, postalCode);
        return this;
    }

    public P05_OverviewPage clickOnContinueButton() {
        Utility.clickElement(driver, continueButtonLocator);
        return new P05_OverviewPage(driver);
    }

    public boolean verifyOverviewPageUrl(String expectedURL) {
        try {
            generalWait(driver).until(ExpectedConditions.urlToBe(expectedURL));
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
