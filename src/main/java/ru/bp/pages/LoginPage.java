package ru.bp.pages;

import static com.codeborne.selenide.Selectors.byText;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

/*
 *   Страница авторизации пользователя
 */

public class LoginPage {
    @Getter
    @FindBy(id = "loginForm")
    private SelenideElement loginForm;

    @FindBy(id = "nameInput")
    private SelenideElement loginInput;

    @FindBy(id = "passwordInput")
    private SelenideElement passwordInput;

    @FindBy(id = "submitButton")
    private SelenideElement submit;

    public static LoginPage open() {
        return Selenide.open("/", LoginPage.class);
    }

    public void login(String username, String password) {
        loginInput.setValue(username);
        passwordInput.setValue(password);
        submit.click();
    }

    public SelenideElement getErrorMessage(String errorMessage) {
        return loginForm.find(byText(errorMessage));
    }

}
