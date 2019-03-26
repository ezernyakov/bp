package ru.bp.context.server.db;

import static java.lang.String.format;

public abstract class DataBases {

    protected final String driver = "org.postgresql.Driver";
    protected final String dbUrl = "jdbc:postgresql://%s:%d/%s";
    protected final String host;
    protected final int port;
    protected final String user;
    protected final String password;

    public DataBases(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }


    protected ITestDataSource createDataSource(String dbName) {
        TestDataSource dataSource = new TestDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(format(dbUrl, host, port, dbName));
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
