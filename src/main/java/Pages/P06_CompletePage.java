package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P06_CompletePage {
    private final WebDriver driver;

    private final By completeOrderLocator = By.cssSelector(".complete-header");


    public P06_CompletePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean checkShownThankYouMessage() {
        return Utility.getElement(driver, completeOrderLocator).isDisplayed();
    }
}
