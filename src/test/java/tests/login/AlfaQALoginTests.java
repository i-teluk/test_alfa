package tests.login;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import screens.EntryResultPage;
import screens.LoginPage;
import tests.BaseTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AlfaQALoginTests extends BaseTest {
    private static final String errorMessage = "Ожидаемый текст не отобразился на экране";
    private static final String errorMaskMessage = "Состояние маскирования не соответствует ожидаемому";

    @Test
    @DisplayName("Проверяем успешное логирование")
    void successfullyLogin() {
        LoginPage loginPage = new LoginPage(driver);
        EntryResultPage entryResultPage = new EntryResultPage(driver);

        loginPage.isDisplayed()
                .login("Login", "Password")
                .waitUntilLoaderDisappear()
                .isDisplayed();

        assertAll("Проверяем отображение текста на двух экранах",
                () -> assertTrue(loginPage.checkTitle("Вход в Alfa-Test"), errorMessage),
                () -> assertTrue(entryResultPage.checkTitle("Вход в Alfa-Test выполнен"), errorMessage)
        );
    }

    static Stream<Arguments> argumentsForTestLoginField() {
        String errorText = "Введены неверные данные";
        return Stream.of(
                arguments("Пустое поле Логин", "", errorText),
                arguments("Логин [A-Z, a-z] длинной 51 символ", RandomStringUtils.randomAlphabetic(51), errorText),
                arguments("Цифры в логине", RandomStringUtils.randomNumeric(10), errorText),
                arguments("Кириллица в логине", "Логин", errorText),
                arguments("Неразрешенные символы в логине", "Login$%", errorText)
        );
    }

    // Запрос authorize не отправляется только в кейсе с 51 символом.
    // Во всех остальных случаях валидация на фронте не работает.
    @ParameterizedTest(name = "{0}")
    @MethodSource("argumentsForTestLoginField")
    @DisplayName("Проверяем валидацию поля Логин, негативные кейсы")
    void checkValidationLoginField(String testName, String login, String errorText) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.isDisplayed()
                .login(login, "Password")
                .waitUntilLoaderDisappear();

        assertTrue(loginPage.checkTitle(errorText), errorMessage);
    }

    @Test
    @DisplayName("Проверяем маскирование поля Пароль")
    void checkTheMasking() {
        LoginPage loginPage = new LoginPage(driver);
        Boolean firstStateIsMasked = loginPage.isDisplayed()
                .enterPassword("Password")
                .checkVisibleOfPassword();
        Boolean secondStateIsUnmasked = loginPage.clickToShowPassword().checkVisibleOfPassword();
        Boolean thirdStateIsMasked = loginPage.clickToShowPassword().checkVisibleOfPassword();

        assertAll("Проверяем состояние маскирования поля Пароль, после каждого нажатия на значок маскирования",
                () -> assertTrue(firstStateIsMasked, errorMaskMessage),
                () -> assertFalse(secondStateIsUnmasked, errorMaskMessage),
                () -> assertTrue(thirdStateIsMasked, errorMaskMessage)
        );
    }
}