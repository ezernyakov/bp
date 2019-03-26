package ru.bp.context.server.db;

public class ServerDataBases extends DataBases {

    private ITestDataSource set;
    private ITestDataSource loyal;
    private ITestDataSource operday;

    public ServerDataBases(String host, int port, String user, String password) {
        super(host, port, user, password);
    }

    public ITestDataSource set() {
        if (set == null) {
            set = createDataSource("set");
        }
        return set;
    }

    public ITestDataSource loyal() {
        if (loyal == null) {
            loyal = createDataSource("set_loyal");
        }
        return loyal;
    }

    public ITestDataSource operday() {
        if (operday == null) {
            operday = createDataSource("set_operday");
        }
        return operday;
    }
}
