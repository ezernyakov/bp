package ru.bp.context.server.db;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.bp.sql.Delete;
import ru.bp.sql.Replace;
import ru.bp.sql.Select;

public interface ITestDataSource extends DataSource {

    JdbcTemplate template();

    default NamedParameterJdbcTemplate namedParameterTemplate() {
        return new NamedParameterJdbcTemplate(this);
    }

    Delete delete(Object entity) throws IllegalAccessException;

    Select select(Object entity) throws IllegalAccessException;

    Replace replace(Object entity) throws IllegalAccessException;
}