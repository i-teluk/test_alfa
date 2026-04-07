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

    private final By title = AppiumBy.className("android.widget.TextView"); //На странице нужно добавить id элемента

    public void isDisplayed() {
        helper.waitUntilElementIsVisible(title);
    }

    public Boolean checkTitle(String expectedText) {
        return helper.isTextOnScreen(expectedText);
    }
}
