package ru.netology.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private final SelenideElement codeInput = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");

    public void confirmVerificationPageIsVisible() {
        codeInput.shouldBe(Condition.visible);
    }

    public DashboardPage validVarificationCode(String verificationCode) {
        verify(verificationCode);
        return new DashboardPage();
    }

    public void verify(String verificationCode) {
        codeInput.setValue(verificationCode);
        verifyButton.click();
    }

    public void confirmErrorNotification(String expectedText) {
        errorNotification.shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.text(expectedText));
    }
}
