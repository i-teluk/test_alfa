package screens;

import helpers.Helper;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class EntryResultPage {
    private AppiumDriver driver;
    private Helper helper;

    public EntryResultPage(AppiumDriver driver) {
        this.driver = driver;
        this.helper = new Helper(driver);
    }

    private final By title = AppiumBy.className("android.widget.TextView"); // На экране разработчику нужно добавить id элемента

    public EntryResultPage isDisplayed() {
        boolean isVisible = driver.findElement(title).isDisplayed();
        if (!isVisible) {
            throw new AssertionError("Entry page is not displayed");
        }
        return this;
    }

    public boolean checkTitle(String expectedText) {
        return driver.findElement(title).getText().equals(expectedText);
    }
}