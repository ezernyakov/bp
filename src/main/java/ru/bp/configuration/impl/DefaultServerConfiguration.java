package ru.bp.configuration.impl;

import ru.bp.configuration.OS;
import ru.bp.configuration.ServerConfiguration;

public class DefaultServerConfiguration implements ServerConfiguration {

    @Override
    public String host() {
        return null;
    }

    @Override
    public int guiPort() {
        return 0;
    }

    @Override
    public int dbPort() {
        return 0;
    }

    @Override
    public String dbLogin() {
        return null;
    }

    @Override
    public String dbPassword() {
        return null;
    }

    @Override
    public String osUser() {
        return null;
    }

    @Override
    public String osPassword() {
        return null;
    }

    @Override
    public OS osType() {
        return null;
    }

    @Override
    public String smbUser() {
        return null;
    }

    @Override
    public String smbPassword() {
        return null;
    }

    @Override
    public String smbDomain() {
        return null;
    }
}
