package ru.netology.delivery.test;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;
import static com.codeborne.selenide.Selenide.*;

class AutoTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void shouldLogin(){
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(Condition.exactText("  Личный кабинет"));
    }

    @Test
    void shouldNotLoginBlockUser() {
        var validUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));
    }

    @Test
    void shouldNotLoginIfRegisteredButLoginIncorrect() {
        var validUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id='password'] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginPassAndLogIncorrect() {
        var validUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id='password'] .input__box .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginIfRegisteredButPassIncorrect() {
        var validUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginIfUserNotRegisteredPassIncorrect(){
        var invalidUser = DataGenerator.Registration.getUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(invalidUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginIfNotRegisteredUserLoginIncorrect(){
        var invalidUser = DataGenerator.Registration.getUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id='password'] .input__box .input__control").val(invalidUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginIfUserNotRegisteredIncorrectLogAndPass() {
        var invalidUser = DataGenerator.Registration.getUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id='password'] .input__box .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginIfRegisteredUserEmptyLogAndPass() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val();
        $("[data-test-id=password] .input__box .input__control").val();
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        $("[data-test-id=password].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotLoginIfValueLoginIsEmpty() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val();
        $("[data-test-id=password] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotLoginIfValuePassIsEmpty() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val();
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}