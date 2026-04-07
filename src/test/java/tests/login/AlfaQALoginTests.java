package tests.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screens.EntryResultPage;
import screens.LoginPage;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlfaQALoginTests extends BaseTest {

    @Test
    @DisplayName("Проверяем успешное логирование")
    void successfullyLogin() {
        LoginPage loginPage = new LoginPage(driver);
        EntryResultPage entryResultPage = new EntryResultPage(driver);

        loginPage.isDisplayed()
                .login("Login", "Password")
                .waitUntilLoaderDisappear()
                .isDisplayed();
        assertAll("",
                () -> assertTrue(loginPage.checkTitle("Вход в Alfa-Test")),
                () -> assertTrue(entryResultPage.checkTitle("Вход в Alfa-Test выполнен"))
        );
    }
}
