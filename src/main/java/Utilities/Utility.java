package Utilities;


import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

// helper (Actions) functions = Wait + Action
public class Utility {

    private static final String screenshotsPath = "Test-Outputs/Screenshots/";

    //TODO: Clicking on element
    //wait element + find + click
    public static void clickElement(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locator)); // wait until the element is visible + enabled
        driver.findElement(locator).click();
    }

    //TODO: Sending data to element
    //wait element + find + send data
    public static void sendData(WebDriver driver, By locator, String data) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

        driver.findElement(locator).sendKeys(data);
    }


    //TODO: Getting text from element
    //wait element + find + get text
    public static String getText(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

        return driver.findElement(locator).getText();
    }


    //TODO: General wait

    public static WebDriverWait generalWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }


    //TODO: Scrolling

    public static void scrolling(WebDriver driver, By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", getElement(driver, locator));
    }


    //TODO: By to WebElement

    public static WebElement getElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }


    //TODO: Getting Timestamp

    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss a").format(new Date());
    }
    //TODO: Taking Screenshots

    public static void takeScreenshot(WebDriver driver, String screenshotName) {
        try {
            // capture screenshot
            File screenSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // saving screenshot
            File screenDestination = new File(screenshotsPath + screenshotName + " - " + getTimeStamp() + ".png");
            FileUtils.copyFile(screenSrc, screenDestination);

            // add screenshot to an allure report
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenDestination.getPath())));
        } catch (Exception e) {
            LogsUtilities.error(e.getMessage());
        }
    }


//    public static void takeScreenshot(WebDriver driver, String screenshotName) {
//        try {
//            // capture full page screenshot using AShot
//            ru.yandex.qatools.ashot.Screenshot screenshot =
//                    new ru.yandex.qatools.ashot.AShot().shootingStrategy(
//                            ru.yandex.qatools.ashot.shooting.ShootingStrategies.viewportPasting(
//                                    (int) Duration.ofMillis(100).toMillis()
//                            )
//                    ).takeScreenshot(driver);
//
//            // saving screenshot
//            File screenDestination = new File(screenshotsPath + screenshotName + " - " + getTimeStamp() + ".png");
//            javax.imageio.ImageIO.write(screenshot.getImage(), "PNG", screenDestination);
//
//            // add screenshot to an allure report
//            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenDestination.getPath())));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//   USING SHUTTERBUG
//    public static void takeScreenshot(WebDriver driver, String screenshotName) {
//        try {
//            // capture full page screenshot using Shutterbug
//            com.assertthat.selenium_shutterbug.utils.web.BrowserViewport viewport = com.assertthat.selenium_shutterbug.utils.web.BrowserViewport.FULL_PAGE;
//            File screenDestination = new File(screenshotsPath + screenshotName + " - " + getTimeStamp() + ".png");
//            com.assertthat.selenium_shutterbug.core.Shutterbug.shootPage(driver)
//                    .withName(screenshotName)
//                    .save(screenshotsPath);
//
//            // add screenshot to an allure report
//            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenDestination.getPath())));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //TODO: Taking Full Screenshot

    public static void takeFullScreeshot(WebDriver driver, By locator) {
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(driver.findElement(locator))
                    .save(screenshotsPath);
        } catch (Exception e) {
            LogsUtilities.error(e.getMessage());
        }
    }

    //TODO: Selecting form dropdown

    public static void selectFromDropdown(WebDriver driver, By locator, String option) {
        new Select(getElement(driver, locator))
                .selectByVisibleText(option);

    }

    //TODO: Generate Random Number

    public static int generateRandomNumber(int upperBound) // from 0 >> upper-1
    {
        return new Random().nextInt(upperBound) + 1;
    }

    public static Set<Integer> generateUniqueNumber(int numberOfNeededProducts, int totalNumberOfProducts) {
        Set<Integer> generateNumbers = new HashSet<>();
        while (generateNumbers.size() < numberOfNeededProducts) {
            int randomNumber = generateRandomNumber(totalNumberOfProducts);
            generateNumbers.add(randomNumber);
        }
        return generateNumbers;
    }

    //TODO: Verifying URLs

    public static boolean verifyUrl(WebDriver driver, String expectedURL) {
        try {
            generalWait(driver).until(ExpectedConditions.urlToBe(expectedURL));
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public static File getLatestFile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0) {
            return null;
        }
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }

    public static Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    public static void restoreSession(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies)
            driver.manage().addCookie(cookie);
    }
}






