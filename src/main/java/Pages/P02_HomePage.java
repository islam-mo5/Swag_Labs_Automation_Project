package Pages;

import Utilities.LogsUtilities;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class P02_HomePage {

    private static List<WebElement> allProducts;
    private static List<WebElement> selectedProducts;
    private final By addToCartButtonFroAll = By.xpath("//button[@class]");
    private final By currentAddedProductsCartIcon = By.cssSelector(".shopping_cart_badge");
    private final By numberOfSelectedProducts = By.xpath("//button[.=\"REMOVE\"]");
    private final By cartIcon = By.id("shopping_cart_container");
    private final By priceOfSelectedProductsLocator = By.xpath("//button[.=\"REMOVE\"]//preceding-sibling::div[@class=\"inventory_item_price\"]");
    private final WebDriver driver;

    //constructor so the driver of TC works here
    public P02_HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public By getNoOfSelectedProductsOnCart() {
        return currentAddedProductsCartIcon;
    }

    //clicking on all 6 products
    public P02_HomePage addProductsToCart() {

        allProducts = driver.findElements(addToCartButtonFroAll);
        LogsUtilities.info("Number of all products are: " + allProducts.size());
        for (int i = 1; i <= allProducts.size(); i++) {
            By addToCartButtonFroAll = By.xpath("(//button[@class])[" + i + "]"); //dynamic locator
            Utility.clickElement(driver, addToCartButtonFroAll);
        }

        return this;
    }

    public String getNumberOfCurrentCartIcon() {
        try {
            LogsUtilities.info("Number of products on cart are: " + Utility.getText(driver, currentAddedProductsCartIcon));
            return Utility.getText(driver, currentAddedProductsCartIcon);
        } catch (Exception e) {
            LogsUtilities.error(e.getMessage());
            return "0";
        }
    }

    public String getNumberOfSelectedProducts() {
        try {
            selectedProducts = driver.findElements(numberOfSelectedProducts);
            LogsUtilities.info("Number of selected products are: " + selectedProducts.size());
            return String.valueOf(selectedProducts.size());
        } catch (Exception e) {
            LogsUtilities.error(e.getMessage());
            return "0";
        }
    }

    //select a random product function
    public P02_HomePage addRandomProducts(int numberOfNeededProducts, int totalNumberOfProducts) {
        Set<Integer> randomNumbers = Utility.generateUniqueNumber(numberOfNeededProducts, totalNumberOfProducts);
        for (int random : randomNumbers) {
            LogsUtilities.info("random Number = " + random);
            By addToCartButtonFroAll = By.xpath("(//button[@class])[" + random + "]"); //dynamic locator
            Utility.clickElement(driver, addToCartButtonFroAll);
        }
        return this;
    }

    public boolean compareNumberOfSelectedProductWithCart() {
        return getNumberOfCurrentCartIcon().equals(getNumberOfSelectedProducts());
    }

    public P03_CartPage clickOnCart() {
        Utility.clickElement(driver, cartIcon);
        return new P03_CartPage(driver);
    }

    public String getTotalPriceOfSelectedProducts() {
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

}
