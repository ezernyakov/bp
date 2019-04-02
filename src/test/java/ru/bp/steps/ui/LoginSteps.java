package ru.bp.steps.ui;

import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ru.bp.pages.LoginPage;

public class LoginSteps {

    private LoginPage loginPage = Selenide.page(LoginPage.class);

    @When("^пользователь логинится на сервере$")
    public void login(String login, String password) {
        loginPage = open("/", LoginPage.class);
        loginPage.login(login, password);
    }

    @Then("^пользователь успешно авторизован$")
    public void passLogin() {
        loginPage.getLoginForm().should(Condition.disappear);
    }

    @Then("^пользователь видит ошибку о неверных параметрах авторизации$")
    public void getErrorMessage() {
        loginPage.getErrorMessage("Неверный логин или пароль").shouldBe(Condition.visible);
    }
}
