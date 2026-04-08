package tests;

import driver.Initializer;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

@Slf4j
public class BaseTest {
    protected AppiumDriver driver;

    @BeforeEach
    public void setUp() {
        log.info("Driver initializing before test");
        driver = Initializer.getDriver();
        log.info("!!!!! Starting the Test !!!!!");
    }

    @AfterEach
    public void tearDown() {
        Initializer.quitDriver();
        log.info("Driver closed after test");
    }
}
