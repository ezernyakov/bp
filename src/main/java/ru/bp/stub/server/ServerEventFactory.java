package ru.bp.stub.server;

import ru.bp.stub.server.entity.ClientEntity;
import ru.bp.stub.server.payloads.FormClientCreateShowPld;

/**
 * Фабрика событий сервера
 */

public class ServerEventFactory {

    public static ServerEvent userLogout() {
        //stub, do something...
        return null;
    }

    public static ServerEvent userLogin(String login, String password) {
        //stub, do something...
        return null;
    }

    public static ServerEventType formLoginShow() {
        //stub, do something...
        return null;
    }

    public static ServerEventType formLogoutShow() {
        //stub, do something...
        return null;
    }

    public static ServerEventType formErrorLoginShow() {
        //stub, do something...
        return null;
    }

    public static ServerEventType formClientShow() {
        //stub, do something...
        return null;
    }

    public static ServerEvent formClientPut(FormClientCreateShowPld formClientCreateShowPld) {
        //stub, do something...
        return null;
    }

    public static ServerEvent formClientCreate(ClientEntity client) {
        //stub, do something...
        return null;
    }

    public static ServerEventType formClientCreateShow() {
        //stub, do something...
        return null;
    }
}
