package ru.bp.context.server.fs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.io.IOUtils;

public class ResourceFile {

    String filename;
    ClassLoader currentClass;
    private InputStream resourceStream;
    private String charset = StandardCharsets.UTF_8.name();

    /**
     * Creates ResourceFile object
     *
     * @param filename relative path to test resource in project folder
     */
    public ResourceFile(String filename) {
        this.filename = filename;
        this.currentClass = Thread.currentThread().getContextClassLoader();
        this.resourceStream = this.currentClass.getResourceAsStream(this.filename);
    }

    public boolean exists() {
        if (this.resourceStream == null) {
            return false;
        }
        return true;
    }

    /**
     * Reads text from ResourceFile
     *
     * @return String with contents of ResourceFile
     * @throws IOException
     */
    public String read() throws IOException {
        if (!exists()) {
            throw new IOException("No file " + filename + " found");
        }
        return IOUtils.toString(resourceStream, charset);
    }

    public byte[] readAsBytes() throws IOException {
        if (!exists()) {
            throw new IOException("No file " + filename + " found");
        }
        return IOUtils.toByteArray(resourceStream);
    }

    public ResourceFile setEncoding(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * Reads text from ResourceFile and replaces wildcard we defined
     * @param replacements
     * @return
     * @throws IOException
     */
    public String readAsBytesAndReplace(Map<String, String> replacements) throws IOException {
        if (!exists()) {
            throw new IOException("No file " + filename + " found");
        }
        String tmp = IOUtils.toString(resourceStream, StandardCharsets.UTF_8.name());

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            tmp = tmp.replaceAll(entry.getKey(), entry.getValue());
        }

        return tmp;
    }

    /**
     * Get abs path to resource file
     */
    public String getPath() throws IOException, URISyntaxException {
        if (!exists()) {
            throw new IOException("No file " + filename + " found");
        }
        return this.currentClass.getResource(this.filename).toURI().getPath();
    }
}
