package screens;

import helpers.Helper;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class LoginPage {
    private AppiumDriver driver;
    private Helper helper;

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        this.helper = new Helper(driver);
    }

    private final By loginField = AppiumBy.id("com.alfabank.qapp:id/etUsername");
    private final By passwordField = AppiumBy.xpath("//android.widget.EditText[@resource-id=\"com.alfabank.qapp:id/etPassword\"]");
    private final By buttonConfirm = AppiumBy.id("com.alfabank.qapp:id/btnConfirm");
    private final By title = AppiumBy.id("com.alfabank.qapp:id/tvTitle");
    private final By errorMassage = AppiumBy.id("com.alfabank.qapp:id/tvError");
    private final By showPassword = AppiumBy.id("com.alfabank.qapp:id/text_input_end_icon");
    private final By loader = AppiumBy.id("com.alfabank.qapp:id/loader");

    public LoginPage isDisplayed() {
        helper.waitUntilElementIsVisible(buttonConfirm);
        return new LoginPage(driver);
    }

    public LoginPage login(String login, String password) {
        driver.findElement(loginField).click();
        driver.findElement(loginField).sendKeys(login);
        driver.findElement(passwordField).click();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(buttonConfirm).click();
        return new LoginPage(driver);
    }

    public Boolean checkTitle(String expectedText) {
        return helper.isTextOnScreen(expectedText);
    }

    public String checkValidationField(String login, String password) {
        driver.findElement(loginField).click();
        driver.findElement(loginField).sendKeys(login);
        driver.findElement(passwordField).click();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(buttonConfirm).click();
        return driver.findElement(errorMassage).getText();
    }

    public LoginPage clickToShowPassword() {
        driver.findElement(showPassword).click();
        return new LoginPage(driver);
    }

    public EntryResultPage waitUntilLoaderDisappear() {
        helper.waitUntilElementDisappear(loader);
        return new EntryResultPage(driver);
    }
}
