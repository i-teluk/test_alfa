package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

@Slf4j
public class Initializer {
    private static AppiumDriver driver;
    static Properties config = new Properties();

    private Initializer() {

    }

    static{
        try (InputStream input = Initializer.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                log.error("Unable to find config.properties\n");
            }
            config.load(input);
        } catch (Exception ex) {
            log.error("Failed to load configuration: \n", ex);
            throw new ExceptionInInitializerError();
        }
    }

    public static AppiumDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    private static void initDriver() {
        try {
            URI appiumServerURI = new URI(config.getProperty("appium.server.url"));
            URL appiumServerUrl = appiumServerURI.toURL();
            driver = new AndroidDriver(appiumServerUrl, getDesiredCapabilities());
            log.info("Driver initialized successfully\n");
        } catch (Exception ex) {
            log.error("Driver initialized failed: \n" + ex);
            throw new RuntimeException();
        }
    }

    private static DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:deviceName", config.getProperty("device.name"));
        capabilities.setCapability("platformName", config.getProperty("platform.name"));
        capabilities.setCapability("appium:app", config.getProperty("app"));
        capabilities.setCapability("appium:appPackage", config.getProperty("app.package"));
        capabilities.setCapability("appium:automationName", config.getProperty("automation.name"));
        capabilities.setCapability("appium:newCommandTimeout", config.getProperty("new.command.timeout"));
        capabilities.setCapability("appium:appActivity", config.getProperty("app.activity"));
        return capabilities;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}