package ru.bp.pages;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.bp.stub.server.entity.ClientEntity;

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

    public void verifyClientExist(ClientEntity client) {
        SelenideElement clientInfo = clientList.$(byText(client.getIdNumber())).parent();
        clientInfo.shouldHave(attribute("lname", client.getLastName()));
        clientInfo.shouldHave(attribute("fname", client.getFirstName()));
        clientInfo.shouldHave(attribute("mname", client.getMiddleName()));
    }

    public void verifyClientAbsent(String tabNumber) {
        clientList.$(byText(tabNumber)).shouldNotBe(Condition.visible);
    }

}
