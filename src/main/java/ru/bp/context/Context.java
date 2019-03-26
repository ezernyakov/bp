package ru.bp.context;

import java.net.MalformedURLException;
import ru.bp.configuration.impl.DefaultServerConfiguration;
import ru.bp.context.server.Server;

public class Context {
    private Server currentServer;

    public Server getCurrentServer() {
        if (currentServer == null) {
            try {
                currentServer = new Server(new DefaultServerConfiguration());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return currentServer;
    }

    public void setCurrentServer(Server server) {
        this.currentServer = server;
    }
}
