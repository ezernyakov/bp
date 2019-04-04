package ru.bp.pages;

import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.bp.stub.server.entity.ClientEntity;

/*
*   Страница редактирования данных клиента
*/

public class ClientPage {
    @FindBy(id = "lastNameInput")
    private SelenideElement lastName;

    @FindBy(id = "firstNameInput")
    private SelenideElement firstName;

    @FindBy(id = "middleNameInput")
    private SelenideElement middleName;

    @FindBy(id = "idNumberInput")
    private SelenideElement id;

    @FindBy(id = "saveButton")
    private SelenideElement saveButton;

    @FindBy(id = "returnButton")
    private SelenideElement returnButton;

    @FindBy(id = "idOrderInput")
    private SelenideElement orderIdInput;

    @FindBy(id = "addOrderButton")
    private SelenideElement addOrderButton;

    @FindBy(id = "orderList")
    private SelenideElement orderList;


    public ClientPage fillClient(ClientEntity client) {
        lastName.setValue(client.getLastName());
        firstName.setValue(client.getFirstName());
        middleName.setValue(client.getMiddleName());
        id.setValue(client.getIdNumber());

        saveButton.click();
        saveButton.shouldBe(Condition.disabled);
        return this;
    }

    public ClientPage addOrderToClient(String orderId) {
        orderIdInput.setValue(orderId);
        addOrderButton.click();
        return this;
    }

    public ClientListPage goToClientList() {
        returnButton.click();
        return page(ClientListPage.class);
    }
}
