package ru.bp.context.server.fs;

import java.io.IOException;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import ru.bp.configuration.SmbConfiguration;
import ru.bp.context.server.Server;

/**
 * Класс для работы с файловой системой сервера.
 */

public class RemoteFileSystem {
    private final SmbConfiguration configuration;
    private final Server server;

    public RemoteFileSystem(Server server) {
        this.server = server;
        this.configuration = server.getConfiguration();
    }

    public void uploadFile(String localFile, String remoteFile) throws IOException {
        RemoteFileWorker.copyFileToRemote(localFile, String.format("%s/%s", server.getHost(), remoteFile),
                configuration.smbUser(), configuration.smbPassword(), configuration.smbDomain());
    }

    public void createDirectory(String directoryPath) throws IOException {
        RemoteFileWorker.makeDirectory(String.format("%s/%s", server.getHost(), directoryPath),
                configuration.smbUser(),
                configuration.smbPassword(),
                configuration.smbDomain());
    }

    public void deleteDirectory(String path) throws IOException {
        RemoteFileWorker.deleteRemoteFileIfExists(String.format("%s/%s", server.getHost(), path),
                configuration.smbUser(),
                configuration.smbPassword(),
                configuration.smbDomain());
    }

    public SmbFile[] getFilesInDirectory(String path) throws SmbException {
        return RemoteFileWorker.getRemoteFile(String.format("%s/%s", server.getHost(), path),
                configuration.smbUser(),
                configuration.smbPassword(),
                configuration.smbDomain()).remoteFile.listFiles();
    }

    public String readFile(String path) throws IOException {
        return RemoteFileWorker.readFile(String.format("%s/%s", server.getHost(), path),
                configuration.smbUser(),
                configuration.smbPassword(),
                configuration.smbDomain());
    }

    public boolean isExists(String path) throws IOException {
        return RemoteFileWorker.isExists(String.format("%s/%s", server.getHost(), path),
                configuration.smbUser(),
                configuration.smbPassword(),
                configuration.smbDomain());
    }
}
