package ru.bp.context.server.db;

import java.util.logging.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
}

