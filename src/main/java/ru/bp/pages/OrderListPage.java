package ru.bp.pages;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/*
 *   Страница поиска счетов
 */

public class OrderListPage {
    @FindBy(id = "addOrderButton")
    private SelenideElement addOrderButton;

    @FindBy(id = "orderFilterInput")
    private SelenideElement orderFilterInput;

    @FindBy(id = "orderFilterButton")
    private SelenideElement orderFilterButton;

    @FindBy(id = "orderList")
    private SelenideElement orderList;

    public OrderPage createNewOrder() {
        addOrderButton.click();
        return page(OrderPage.class);
    }

    public OrderPage findOrder(String value) {
        orderFilterInput.setValue(value);
        orderList.$(byText(value)).click();
        orderFilterButton.shouldBe(Condition.enabled);

        orderFilterButton.click();
        return page(OrderPage.class);
    }

}
