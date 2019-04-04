package ru.bp.context.server.db;

public class ServerDataBases extends DataBases {

    private ITestDataSource client;
    private ITestDataSource order;

    public ServerDataBases(String host, int port, String user, String password) {
        super(host, port, user, password);
    }

    public ITestDataSource clients() {
        if (client == null) {
            client = createDataSource("clients");
        }
        return client;
    }

    public ITestDataSource orders() {
        if (order == null) {
            order = createDataSource("orders");
        }
        return order;
    }
}
