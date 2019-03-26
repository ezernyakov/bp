package ru.bp.context.server;


import java.net.MalformedURLException;
import java.net.URL;
import ru.bp.configuration.ServerConfiguration;
import ru.bp.context.server.db.ServerDataBases;
import ru.bp.context.server.fs.RemoteFileSystem;

/**
 * Объектное представление тестового сервера.
 */

public class Server {

    private final URL url;
    private final ServerConfiguration configuration;
    private ServerDataBases db;
    private RemoteFileSystem io;


    public Server(ServerConfiguration serverConfiguration) throws MalformedURLException {
        this.configuration = serverConfiguration;
        this.url = new URL("http", serverConfiguration.host(), serverConfiguration.guiPort(), "");
    }

    /**
     * Базы данных сервера.
     *
     * @return класс для взаимодействия с базами данных сервера.
     */
    public ServerDataBases db() {
        if (db == null) {
            db = new ServerDataBases(url.getHost(), configuration.dbPort(), configuration.dbLogin(), configuration.dbPassword());
        }
        return db;
    }

    /**
     * Файловая система сервера.
     *
     * @return класс для взаимодействия с удаленной файловой системой сервера.
     */
    public RemoteFileSystem fs() throws MalformedURLException {
        if (io == null) {
            io = new RemoteFileSystem(this);
        }
        return io;
    }

    public String getHost() {
        return configuration.host();
    }

    public ServerConfiguration getConfiguration() {
        return configuration;
    }

}
