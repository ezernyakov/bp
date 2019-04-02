package ru.bp.pages;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/*
 *   Страница со списком клиентов
 */

public class ClientListPage {
    @FindBy(id = "addClientButton")
    private SelenideElement addClientButton;

    @FindBy(id = "clientList")
    private SelenideElement clientList;


    public ClientPage createNewClient() {
        addClientButton.click();
        return page(ClientPage.class);
    }

    public ClientPage openClient(String id) {
        clientList.$(byText(id)).hover()
                .parent().parent()
                .$(byAttribute("title", "Редактировать"))
                .click();
        return page(ClientPage.class);
    }

    public void verifyClientExist(String tabNumber) {
        clientList.$(byText(tabNumber)).shouldBe(Condition.visible);
    }

    public void verifyClientAbsent(String tabNumber) {
        clientList.$(byText(tabNumber)).shouldNotBe(Condition.visible);
    }

}
