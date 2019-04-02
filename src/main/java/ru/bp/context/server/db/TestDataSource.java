package ru.bp.context.server.db;

import java.util.logging.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.bp.sql.Delete;
import ru.bp.sql.JpaSqlParameterSource;
import ru.bp.sql.Replace;
import ru.bp.sql.Select;
import ru.bp.sql.Sql;

public class TestDataSource extends DriverManagerDataSource implements ITestDataSource {

    public TestDataSource() {
        super();
    }

    public JdbcTemplate template() {
        return new JdbcTemplate(this);
    }

    public Delete delete(Object entity) throws IllegalAccessException {
        return new Delete(this, Sql.generateFor(entity.getClass()), new JpaSqlParameterSource(entity));
    }

    public Select select(Object entity) throws IllegalAccessException {
        return new Select(this, Sql.generateFor(entity.getClass()), new JpaSqlParameterSource(entity));
    }

    public Replace replace(Object entity) throws IllegalAccessException {
        return new Replace(this, Sql.generateFor(entity.getClass()), new JpaSqlParameterSource(entity));
    }

    @Override
    public Logger getParentLogger() {
        return Logger.getLogger("global");
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return false;
    }
}

