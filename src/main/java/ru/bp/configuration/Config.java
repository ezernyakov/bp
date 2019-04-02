package ru.bp.configuration;

import org.apache.commons.configuration2.CombinedConfiguration;

public class Config {

    public static String SERVER_HOST;
    public static String DEFAULT_PORT;
    public static OS OS_TYPE;
    public static String OS_USERNAME;
    public static String OS_PASSWORD;
    public static String SMB_USERNAME;
    public static String SMB_PASSWORD;
    public static String SMB_DOMAIN;

    public static String DB_USER;
    public static String DB_PASSWORD;
    public static String DB_PORT;

    static {
        CombinedConfiguration configuration = ConfigLoader.getConfiguration();

        //server
        SERVER_HOST = configuration.getString("server.host.ip", "127.0.0.1");
        DEFAULT_PORT = configuration.getString("port", "8080");

        //file system
        OS_TYPE = OS.getOSByName(configuration.getString("os.type", "LINUX"));
        SMB_USERNAME = configuration.getString("smb.linux.username", "user");
        SMB_PASSWORD = configuration.getString("smb.linux.password", "password");
        SMB_DOMAIN = configuration.getString("smb.linux.domain", "");
        OS_USERNAME = configuration.getString("os.username", "root");
        OS_PASSWORD = configuration.getString("os.password", "password");

        //db
        DB_USER = configuration.getString("db.user", "postgres");
        DB_PASSWORD = configuration.getString("db.password", "postgres");
        DB_PORT = configuration.getString("db.port", "5432");
    }
}
