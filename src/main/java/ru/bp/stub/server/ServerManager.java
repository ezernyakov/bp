package ru.bp.stub.server;

import ru.bp.stub.server.entity.ClientEntity;

public interface ServerManager {

    /*
    * создает клиента на сервера
    * */
    void addClient(ClientEntity client);
}
