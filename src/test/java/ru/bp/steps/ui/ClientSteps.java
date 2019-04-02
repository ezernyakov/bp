package ru.bp.steps.ui;

import com.codeborne.selenide.Selenide;

import cucumber.api.java.en.Given;
import java.util.List;
import ru.bp.context.Context;
import ru.bp.pages.ClientListPage;
import ru.bp.pages.ClientPage;
import ru.bp.stub.server.entity.ClientEntity;

public class ClientSteps {

    private Context context;
    private ClientListPage clientListPage = Selenide.page(ClientListPage.class);

    public ClientSteps(Context context) {
        this.context = context;
    }

    @Given("^на сервере нет клиентов с идентификатором:$")
    public void clearCashiers(List<String> ids) throws IllegalAccessException {
        for (String id : ids) {
            ClientEntity client = new ClientEntity();
            client.setIdNumber(id);

            context.getCurrentServer().db().clients().delete(client).byPk();
        }
    }

    @Given("^на сервере заведены клиенты$")
    public void createUser(List<ClientsVO> clients) {
        ServerManager manager = context.getCurrentServer().rmi(ServerManager.class, Jndi.CLIENTS.getName());
        for (ClientsVO client : clients) {
            manager.addCashier(null, client);
        }
    }


}
