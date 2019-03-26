package ru.bp.configuration;

/**
 * Описание конфигурации операционной системы
 */

public interface OSConfiguration {

    String osUser();

    String osPassword();

    OS osType();
}
