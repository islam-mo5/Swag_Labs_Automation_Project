package Pages;

import Utilities.LogsUtilities;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {
    private final WebDriver driver;

    private final By subTotalPriceLocator = By.cssSelector(".summary_subtotal_label");
    private final By taxPriceLocator = By.cssSelector(".summary_tax_label");
    private final By totalPriceLocator = By.cssSelector(".summary_total_label");
    private final By finishButtonLocator = By.cssSelector(".cart_button");

    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }


    public Float getSubTotal() {
        return Float.parseFloat(Utility.getText(driver, subTotalPriceLocator).replace("Item total: $", ""));
    }

    public Float getTax() {
        return Float.parseFloat(Utility.getText(driver, taxPriceLocator).replace("Tax: $", ""));
    }

    public Float getTotal() {
        LogsUtilities.info("Actual Total Price = " + Utility.getText(driver, totalPriceLocator).replace("Total: $", ""));
        return Float.parseFloat(Utility.getText(driver, totalPriceLocator).replace("Total: $", ""));
    }

    public String calculateTotalPrice() {
        float subtotal = getSubTotal();
        float tax = getTax();
        float total = subtotal + tax;

        LogsUtilities.info("calculating SubTotal = " + subtotal + " and Tax = " + tax + " = " + total);

        // Round to 2 decimal places and return as string
        return String.format("%.2f", total);
    }


    public boolean compareFinalPrices() {
        return calculateTotalPrice().equals(String.format("%.2f", getTotal()));
    }

    public P06_CompletePage clickOnFinishButton() {
        Utility.clickElement(driver, finishButtonLocator);
        return new P06_CompletePage(driver);
    }


}
