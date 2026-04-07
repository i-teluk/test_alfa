package tests;
import driver.Initializer;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AlfaQALoginTests {

    @BeforeAll
    static void prepareBeforeAll() {
        AppiumDriver driver = Initializer.getDriver();
    }

    @AfterAll
    static void restore() {
        Initializer.quitDriver();
    }

    @Test
    void successfullyLogin() {

    }
}
