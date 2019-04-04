package ru.bp.steps.backend;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ru.bp.context.User;
import ru.bp.stub.server.ServerEvent;
import ru.bp.stub.server.ServerEventFactory;
import ru.bp.stub.server.payloads.FormLoginShowPld;
import ru.bp.websocket.WebSocketWrapper;

public class LoginSteps {

    private WebSocketWrapper socket;

    public LoginSteps(WebSocketWrapper socket) {
        this.socket = socket;
    }


    @When("{user} авторизуется на сервере")
    public void login(User user) {
        ServerEvent event = ServerEventFactory.userLogin(user.getLogin(), user.getPassword());
        socket.send(event);
        socket.receive(ServerEventFactory.formLoginShow(), FormLoginShowPld.class);
    }

    @When("^пользователь логинится на сервере$")
    public void login(String login, String password) {
        ServerEvent event = ServerEventFactory.userLogin(login, password);
        socket.send(event);
    }

    @Then("^пользователь видит ошибку о неверных параметрах авторизации$")
    public void getErrorMessage() {
        socket.receive(ServerEventFactory.formErrorLoginShow(), FormLoginShowPld.class);
    }
}
