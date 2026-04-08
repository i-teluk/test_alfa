package helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Helper {

    private AppiumDriver driver;
    private WebDriverWait wait;

    public Helper(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void waitUntilElementIsVisible(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public void waitUntilElementDisappear(By element) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }
}