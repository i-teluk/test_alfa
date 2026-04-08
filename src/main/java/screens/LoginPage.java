package screens;

import helpers.Helper;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
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
        return this;
    }

    public LoginPage enterLogin(String login) {
        log.info("Enter login: [{}]", login);
        driver.findElement(loginField).click();
        driver.findElement(loginField).sendKeys(login);
        return this;
    }

    public LoginPage enterPassword(String password) {
        log.info("Enter password: [{}]", password);
        driver.findElement(passwordField).click();
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    public LoginPage clickLoginButton() {
        log.info("Tap on Login button");
        driver.findElement(buttonConfirm).click();
        return this;
    }

    public LoginPage login(String login, String password) {
        enterLogin(login);
        enterPassword(password);
        clickLoginButton();
        return this;
    }

    public Boolean checkTitle(String expectedText) {
        return helper.isTextOnScreen(expectedText);
    }

    public Boolean checkVisibleOfPassword() {
        String state = driver.findElement(passwordField).getAttribute("password");
        if (state.equals("true")) {
            log.info("The password is masked.");
            return true;
        } else {
            log.info("The password is not masked.");
            return false;
        }
    }

    public LoginPage clickToShowPassword() {
        driver.findElement(showPassword).click();
        return this;
    }

    public EntryResultPage waitUntilLoaderDisappear() {
        helper.waitUntilElementDisappear(loader);
        log.info("Loader is disappeared");
        return new EntryResultPage(driver);
    }
}