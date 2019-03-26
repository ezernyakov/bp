package ru.bp.configuration;

/**
 * Описание конфигурации доступа к операционной системе
 */

public interface SmbConfiguration {

    String smbUser();

    String smbPassword();

    String smbDomain();
}
