package ru.bp.configuration;

/**
 * Описание конфигурации сервера.
 *
 */

public interface ServerConfiguration extends SmbConfiguration, OSConfiguration, DbConfiguration {

    /**
     * Хост сервера.
     */
    String host();

    /**
     * порт  визуализации сервера
     */
    int guiPort();
}
