package ru.bp.context.server.fs;

import jcifs.smb.NtlmPasswordAuthentication;

import java.io.IOException;

/**
 * Class for working with remote files through SMB/CIFS
 */

public class RemoteFileWorker {

    /**
     * Creates new authentication for SMB
     *
     * @param username
     * @param password
     * @param domain
     * @return Authentication object for RemoteFile
     */
    public static NtlmPasswordAuthentication createAuthentication(String username, String password, String domain) {
        return new NtlmPasswordAuthentication(domain, username, password);
    }

    /**
     * Reads remote file on specified path
     *
     * @param path
     * @param username
     * @param password
     * @param domain
     * @return
     * @throws Exception
     */
    public static String readFile(String path, String username, String password, String domain) throws IOException {
        RemoteFile file = new RemoteFile(path, createAuthentication(username, password, domain));
        return file.read();
    }

    /**
     * Writes text to remote file
     *
     * @param text
     * @param path
     * @param username
     * @param password
     * @param domain
     * @throws Exception
     */
    public static void writeStringToFile(String text, String path, String username, String password, String domain) throws IOException {
        RemoteFile file = new RemoteFile(path, createAuthentication(username, password, domain));
        file.write(text);
    }

    private static void writeBytesToFile(byte[] content, String path, String username, String password, String domain) throws IOException {
        RemoteFile file = new RemoteFile(path, createAuthentication(username, password, domain));
        file.write(content);
    }

    public static void renameFile(String path, String newPath, String username, String password, String domain) throws IOException {
        RemoteFile fileNew = new RemoteFile(newPath, createAuthentication(username, password, domain));
        if (fileNew.isExists()) {
            fileNew.delete();
        }
        RemoteFile file = new RemoteFile(path, createAuthentication(username, password, domain));
        file.rename(newPath);
    }

    /**
     * Creates remote directory
     *
     * @param path
     * @param username
     * @param password
     * @param domain
     * @throws IOException
     */
    public static void makeDirectory(String path, String username, String password, String domain) throws IOException {
        RemoteFile file = new RemoteFile(path, createAuthentication(username, password, domain));
        if (!file.isExists()) {
            file.makeDir();
        }
    }

    /**
     * Replaces text in remote file
     *
     * @param regex
     * @param replacement
     * @param path
     * @param username
     * @param password
     * @param domain
     * @throws Exception
     */
    public static void replaceTextInFile(String regex, String replacement, String path, String username, String password, String domain) throws IOException {
        RemoteFile file = new RemoteFile(path, createAuthentication(username, password, domain));
        file.replaceString(regex, replacement);
    }

    /**
     * Copies file to remote SMB share with specified name
     *
     * @param fileToCopy
     * @param destinationPath Full path with filename
     * @param username
     * @param password
     * @param domain
     * @throws Exception
     */
    public static void copyFileToRemote(String fileToCopy, String destinationPath, String username, String password, String domain) throws IOException {
        ResourceFile sourceFile = new ResourceFile(fileToCopy);
        writeBytesToFile(sourceFile.readAsBytes(), destinationPath + ".temp", username, password, domain);
        renameFile(destinationPath + ".temp", destinationPath, username, password, domain);
    }

    /**
     * Verifies if file exists on remote SMB filesystem
     *
     * @param path
     * @param username
     * @param password
     * @param domain
     * @return
     * @throws Exception
     */
    public static boolean isExists(String path, String username, String password, String domain) throws IOException {
        RemoteFile file = new RemoteFile(path, createAuthentication(username, password, domain));
        return file.isExists();
    }

    /**
     * Deletes if file exists on remote SMB filesystem
     *
     * @param path     путь
     * @param username логин для входа на удаленный ресурс
     * @param password пароль для входа на удаленный ресурс
     * @param domain   домен
     * @throws IOException если при удалении объект не найден, то выкидывается с уровня RemoteFile
     */
    public static void deleteRemoteFileIfExists(String path, String username, String password, String domain) throws IOException {
        RemoteFile file = new RemoteFile(path, createAuthentication(username, password, domain));
        if (file.isExists()) {
            file.delete();
        }
    }

    /**
     * Получить удаленный объект.
     *
     * @param path     путь
     * @param username логин для входа на удаленный ресурс
     * @param password пароль для входа на удаленный ресурс
     * @param domain   домен
     * @return объект(директория или файл) с удаленного сервера
     */
    public static RemoteFile getRemoteFile(String path, String username, String password, String domain) {
        return new RemoteFile(path, createAuthentication(username, password, domain));
    }
}