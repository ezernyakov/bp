package ru.bp.pages;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.support.FindBy;
import ru.bp.stub.server.entity.OrderOperationsEntity;

/*
 *   Страница просмотра данных счета и операций по счету
 */

public class OrderPage {

    @FindBy(id = "client")
    private SelenideElement client;

    @FindBy(id = "saveButton")
    private SelenideElement saveButton;

    @FindBy(id = "returnButton")
    private SelenideElement returnButton;

    @FindBy(id = "orderOperationsList")
    private SelenideElement orderOperationsList;

    @FindBy(id = "freezeButton")
    private SelenideElement freezeButton;

    @FindBy(id = "orderStatus")
    private SelenideElement status;


    public OrderPage freezeOrder() {
        freezeButton.click();
        status.$(byText("Счёт заморожен")).shouldBe(Condition.visible);

        saveButton.click();
        saveButton.shouldBe(Condition.disabled);
        return this;
    }

    public List<OrderOperationsEntity> getOrderOperations() {
        List<OrderOperationsEntity> operations = new ArrayList<>();
        ElementsCollection operationElements = orderOperationsList.findAll(byId("operationOrder"));

        for(SelenideElement operationElement: operationElements) {
            String type = operationElement.$(byId("type")).getText();
            String sum = operationElement.$(byId("sum")).getText();
            OrderOperationsEntity entity = new OrderOperationsEntity();
            entity.setType(type);
            entity.setSumOperation(type);

            operations.add(entity);
        }

        return operations;
    }

    public OrderListPage goToOrderList() {
        returnButton.click();
        return page(OrderListPage.class);
    }
}
