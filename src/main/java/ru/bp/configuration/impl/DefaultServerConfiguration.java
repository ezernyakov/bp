package ru.bp.configuration.impl;

import ru.bp.configuration.Config;
import ru.bp.configuration.OS;
import ru.bp.configuration.ServerConfiguration;
/*
* Конфигурация сервера по-умолчанию
* */
public class DefaultServerConfiguration implements ServerConfiguration {

    @Override
    public String host() {
        return Config.SERVER_HOST;
    }

    @Override
    public int guiPort() {
        return Integer.parseInt(Config.DEFAULT_PORT);
    }

    @Override
    public int dbPort() {
        return Integer.parseInt(Config.DB_PORT);
    }

    @Override
    public String dbLogin() {
        return Config.DB_USER;
    }

    @Override
    public String dbPassword() {
        return Config.DB_PASSWORD;
    }

    @Override
    public String osUser() {
        return Config.OS_USERNAME;
    }

    @Override
    public String osPassword() {
        return Config.OS_PASSWORD;
    }

    @Override
    public OS osType() {
        return Config.OS_TYPE;
    }

    @Override
    public String smbUser() {
        return Config.SMB_USERNAME;
    }

    @Override
    public String smbPassword() {
        return Config.SMB_PASSWORD;
    }

    @Override
    public String smbDomain() {
        return Config.SMB_DOMAIN;
    }
}
