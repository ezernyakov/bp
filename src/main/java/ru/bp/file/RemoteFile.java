package ru.bp.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class RemoteFile {
    String filePath;
    private NtlmPasswordAuthentication smbAuth;
    public SmbFile remoteFile;

    /**
     * Creates remote file object with path and SMB authentication, ready writing/reading
     * @param filePath
     * @param smbAuth
     */
    public RemoteFile(String filePath, NtlmPasswordAuthentication smbAuth) {
        this.filePath = "smb://" + filePath;
        this.smbAuth = smbAuth;
        try {
            remoteFile = new SmbFile(this.filePath, getSmbAuth());
        } catch (MalformedURLException e) {

        }

    }

    /**
     * Performs remote file reading.
     * @return String with file contents
     * @throws IOException
     */
    public String read() throws IOException {
        StringBuilder resultString = new StringBuilder();
        String lineReader;

        SmbFileInputStream smbinput = new SmbFileInputStream(remoteFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(smbinput, Charset.forName("UTF-8")));

        while ((lineReader = reader.readLine()) != null ){
            resultString.append(lineReader).append("\r\n");
        }
        reader.close();

        return resultString.toString();
    }

    /**
     * Creates remote directory
     * @throws IOException
     */
    public void makeDir() throws IOException {
        remoteFile.mkdir();
    }

    /**
     * Performs remote file text writing
     * @param text  string for writing to file
     * @throws Exception
     */
    public void write(String text) throws IOException {
        write(text.getBytes(Charset.forName("UTF-8")));
    }

    /**
     * Performs remote file byte array writing
     * @param content array for writing to file
     * @throws Exception
     */
    public void write(byte[] content) throws IOException {
        SmbFileOutputStream smboutput;

        try {
            smboutput = new SmbFileOutputStream(remoteFile);
        } catch (SmbException e) {
            throw new IOException("Error in SMB module", e);
        } catch (MalformedURLException e) {
            throw new IOException("File URL is wrong", e);
        } catch (UnknownHostException e) {
            throw new IOException("Can't resolve remote host for SMB", e);
        }

        smboutput.write(content);

        smboutput.close();
    }

    /**
     * Replaces text matched by regex with specified replacement
     * @param regex String with regular expression for matching
     * @param replacement String with replacement
     * @throws Exception
     */
    public void replaceString(String regex, String replacement) throws IOException {
        write(read().replaceAll(regex, replacement));
    }

    /**
     * Получение объекта аутентификации для CIFS
     * @return
     */
    public NtlmPasswordAuthentication getSmbAuth() {
        return this.smbAuth;
    }

    /**
     * Проверяет, существует ли файл по пути, относящемуся к текущему объекту
     * @return
     * @throws SmbException
     */
    public boolean isExists() throws SmbException {
        return remoteFile.exists();
    }

    /**
     * Переименование файла, относящегося к текущему объекту
     * @param newPath
     * @throws IOException
     */
    public void rename(String newPath) throws IOException {
        try {
            SmbFile newFile = new SmbFile("smb://" + newPath, getSmbAuth());
            remoteFile.renameTo(newFile);
            remoteFile = newFile;
        } catch (MalformedURLException e) {
            throw new IOException("Неправильный путь к файлу");
        } catch (SmbException e) {
            throw new IOException("Не удалось переименовать файл");
        }
    }

    /**
     * Удаление файла, относящегося к текущему объекту
     *
     * @throws SmbException если нет файла/директории, то выкидываем ошибку
     */
    public void delete() throws SmbException {
        remoteFile.delete();
    }
}
