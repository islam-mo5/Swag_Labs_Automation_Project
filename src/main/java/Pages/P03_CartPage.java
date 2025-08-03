package Pages;

import Utilities.LogsUtilities;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


public class P03_CartPage {

    private final WebDriver driver;

    private final By priceOfSelectedProductsLocator = By.xpath("//button[.=\"REMOVE\"]//preceding-sibling::div[@class=\"inventory_item_price\"]");
    private final By checkoutButton = By.cssSelector(".checkout_button");


    //constructor to deal with the driver in click on cart function in TC02
    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public P04_CheckoutPage clickOnCheckoutButton() {
        Utility.clickElement(driver, checkoutButton);
        return new P04_CheckoutPage(driver);
    }

    //TODO: FIX THIS FUNCTION --> IT MULTIPLES THE TOTAL PRICE BY 2
    public String getTotalPriceInCart() {
        float totalPrice = 0;

        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(priceOfSelectedProductsLocator);
            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By elements = By.xpath("(//button[.=\"REMOVE\"]/preceding-sibling::div[@class=\"inventory_item_price\"])[" + i + "]"); //dynamic locator
                String fullText = Utility.getText(driver, elements);
                totalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            LogsUtilities.info("Total price = " + totalPrice);
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtilities.error(e.getMessage());
            return "0";
        }
    }

    public boolean comparePrices(String price) {
        return getTotalPriceInCart().equals(price);
    }
}
