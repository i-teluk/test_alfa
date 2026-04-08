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
    private static final String errorMessage = "Ожидаемое сообщение об ошибке не отобразилось: %s";
    private static final String errorMaskMessage = "Состояние маскирования не соответствует ожидаемому";

    @Test
    @DisplayName("Проверяем успешное логирование")
    void successfullyLogin() {
        String expectedText = "Вход в Alfa-Test выполнен";
        EntryResultPage entryResultPage = new LoginPage(driver)
                .isDisplayed()
                .login("Login", "Password")
                .waitUntilLoaderDisappear()
                .isDisplayed();
        assertTrue(entryResultPage.checkTitle(expectedText), errorMessage.formatted(expectedText));
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

    // Запрос authorize не отправляется только в кейсах с 51 символом и пустым полем.
    // Во всех остальных случаях валидация на фронте не работает и запрос отправляется.
    // В позитивных кейсах, нужно проверять, что отправляется запрос authorize с мобилки,
    // но у меня не получилось подключить charles к эмуляции.
    @ParameterizedTest(name = "{0}")
    @MethodSource("argumentsForTestLoginField")
    @DisplayName("Проверяем валидацию поля Логин, отображение InvalidValue")
    void checkValidationLoginField(String testName, String login, String errorText) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.isDisplayed()
                .login(login, "Password")
                .waitUntilLoaderDisappear();

        assertTrue(loginPage.checkErrorMessage(errorText), errorMessage.formatted(errorText));
    }

    @Test
    @DisplayName("Проверяем маскирование поля Пароль")
    void checkTheMasking() {
        LoginPage loginPage = new LoginPage(driver);
        boolean firstStateIsMasked = loginPage.isDisplayed()
                .enterPassword("Password")
                .checkVisibleOfPassword();
        boolean secondStateIsUnmasked = loginPage.clickToShowPassword().checkVisibleOfPassword();
        boolean thirdStateIsMasked = loginPage.clickToShowPassword().checkVisibleOfPassword();

        assertAll("Проверяем состояние маскирования поля Пароль, после каждого нажатия на значок маскирования",
                () -> assertTrue(firstStateIsMasked, errorMaskMessage),
                () -> assertFalse(secondStateIsUnmasked, errorMaskMessage),
                () -> assertTrue(thirdStateIsMasked, errorMaskMessage)
        );
    }

    static Stream<Arguments> argumentsForTestPasswordField() {
        String errorText = "Введены неверные данные";
        return Stream.of(
                arguments("Пустое поле Пароль", "", errorText),
                arguments("Пароль [A-Z, a-z] длинной 51 символ", RandomStringUtils.randomAlphabetic(51), errorText),
                arguments("Кириллица в Пароле", "Пароль", errorText)
        );
    }

    // Запрос authorize не отправляется только в кейсах с 51 символом и пустым полем.
    // Во всех остальных случаях валидация на фронте не работает.
    @ParameterizedTest(name = "{0}")
    @MethodSource("argumentsForTestPasswordField")
    @DisplayName("Проверяем валидацию поля Пароль, отображение InvalidValue")
    void checkValidationPasswordField(String testName, String password, String errorText) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.isDisplayed()
                .login("Login", password)
                .waitUntilLoaderDisappear();

        assertTrue(loginPage.checkErrorMessage(errorText), errorMessage.formatted(errorText));
    }

    //Согласно требованиям, должно быть другое валидационное сообщение.
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверяем вставку в поле Логин из буфера обмена и отображение ExceptValue")
    @MethodSource("argumentsForTestLoginField")
    void checkInputFromClipboardForLoginField(String testName, String login, String errorText) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.isDisplayed()
                .enterLoginFromClipboard(login)
                .enterPassword("Password")
                .clickLoginButton()
                .waitUntilLoaderDisappear();

        assertTrue(loginPage.checkErrorMessage(errorText), errorMessage.formatted(errorText));
    }
}