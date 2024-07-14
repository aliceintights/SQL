package ru.netology.web.test;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLHelper;
import ru.netology.web.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.SQLHelper.*;

public class SQLLoginTest {

    LoginPage loginPage;

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @Test
    void shouldSuccessfullyLogIn() {
        loginPage = open("http://localhost:9999/", LoginPage.class);
        var loginInfo = DataHelper.getTestLoginInfo();
        var verificationPage = loginPage.validLogin(loginInfo);
        verificationPage.confirmVerificationPageIsVisible();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.verify(verificationCode.getCode());
    }

    @Test
    void shouldTestRandomUserData() {
        loginPage = open("http://localhost:9999/", LoginPage.class);
        var loginInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(loginInfo);
        loginPage.confirmErrorNotification("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldTestValidLoginInfoButRandomVerificationCode() {
        loginPage = open("http://localhost:9999/", LoginPage.class);
        var loginInfo = DataHelper.getTestLoginInfo();
        var verificationPage = loginPage.validLogin(loginInfo);
        verificationPage.confirmVerificationPageIsVisible();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.confirmErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }
}
