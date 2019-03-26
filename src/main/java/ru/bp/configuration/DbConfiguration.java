package ru.bp.configuration;

/**
 * Описание доступа к БД
 */

public interface DbConfiguration {

    int dbPort();

    String dbLogin();

    String dbPassword();
}
