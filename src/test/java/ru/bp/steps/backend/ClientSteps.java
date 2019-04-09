package ru.bp.steps.backend;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.List;
import org.junit.Assert;
import ru.bp.context.Context;
import ru.bp.stub.server.ServerEvent;
import ru.bp.stub.server.ServerEventFactory;
import ru.bp.stub.server.entity.ClientEntity;
import ru.bp.stub.server.payloads.FormClientCreateShowPld;
import ru.bp.stub.server.payloads.FormClientShowPld;
import ru.bp.websocket.WebSocketWrapper;

public class ClientSteps {
    private Context context;
    private WebSocketWrapper socket;

    public ClientSteps(Context context, WebSocketWrapper socket) {
        this.context = context;
        this.socket = socket;
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
        for(ClientEntity client: clients) {
            createClient(client);
        }
    }

    @When("(Администратор/Менеджер )?создает нового клиента")
    public void createClient(ClientEntity client) {
        // открыть форму создания клиента
        ServerEvent event = ServerEventFactory.formClientPut(new FormClientCreateShowPld());
        socket.send(event);
        socket.receive(ServerEventFactory.formClientShow(), FormClientShowPld.class);

        // отправить клиента
        sendClient(client);
    }

    @When("(Администратор/Менеджер )?редактирует клиента")
    public void editClient(ClientEntity client) {
        // открыть форму редактирования клиента
        ServerEvent event = ServerEventFactory.formClientPut(new FormClientCreateShowPld(client.getIdNumber()));
        socket.send(event);
        socket.receive(ServerEventFactory.formClientShow(), FormClientShowPld.class);

        // отправить клиента
        sendClient(client);
    }

    private void sendClient(ClientEntity client) {
        ServerEvent event = ServerEventFactory.formClientCreate(client);
        socket.send(event);
        FormClientCreateShowPld clientForm = socket.receive(ServerEventFactory.formClientCreateShow(), FormClientCreateShowPld.class);
        Assert.assertTrue(clientForm.isSuccess());
    }

    @Then("в системе есть клиент")
    public void verifyClientExist(ClientEntity client) throws IllegalAccessException {
        Assert.assertNotNull(context.getCurrentServer().db().clients().select(client).byPk());
    }

}
