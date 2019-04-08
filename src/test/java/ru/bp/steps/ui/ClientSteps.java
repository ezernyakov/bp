package ru.bp.steps.ui;

import com.codeborne.selenide.Selenide;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.List;
import ru.bp.context.Context;
import ru.bp.pages.ClientListPage;
import ru.bp.stub.server.ServerManager;
import ru.bp.stub.server.entity.ClientEntity;

public class ClientSteps {

    private Context context;
    private ClientListPage clientListPage = Selenide.page(ClientListPage.class);

    public ClientSteps(Context context) {
        this.context = context;
    }

    @Given("^на сервере нет клиентов с идентификатором:$")
    public void clearClients(List<String> ids) throws IllegalAccessException {
        for (String id : ids) {
            ClientEntity client = new ClientEntity();
            client.setIdNumber(id);

            context.getCurrentServer().db().clients().delete(client).byPk();
        }
    }

    @Given("^на сервере заведены клиенты$")
    public void createClients(List<ClientEntity> clients) {
        ServerManager manager = context.getCurrentServer().rmi(ServerManager.class, "<jndi-name>");
        for(ClientEntity client: clients) {
            manager.addClient(client);
        }
    }

    @When("(Администратор/Менеджер )?создает нового клиента")
    public void createClient(ClientEntity client) {
        clientListPage.createNewClient()
                .fillClient(client)
                .goToClientList();
    }

    @When("(Администратор/Менеджер )?редактирует клиента")
    public void editClient(ClientEntity client) {
        clientListPage.openClient(client.getIdNumber())
                .fillClient(client)
                .goToClientList();
    }

    @Then("в списке есть клиент")
    public void verifyClientExist(ClientEntity client) {
        clientListPage.verifyClientExist(client);
    }

    @Then("в списке отсутствует клиент с идентификатором {string}")
    public void verifyClientAbsent(String id) {
        clientListPage.verifyClientAbsent(id);
    }
}
