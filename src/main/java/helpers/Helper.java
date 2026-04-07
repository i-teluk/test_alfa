package helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Helper {

    private AppiumDriver driver;

    public Helper(AppiumDriver driver) {
        this.driver = driver;
    }

    public Boolean isTextOnScreen(String expectedText) {
        return driver.getPageSource().contains(expectedText);
    }

    public void waitUntilElementIsVisible(By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public void waitUntilElementDisappear(By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }
}