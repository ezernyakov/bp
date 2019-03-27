package ru.bp.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class JpaRowMapper implements RowMapper {

    private final JpaTable<?> table;

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Object instance;
        try {
            instance = table.entityClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Exception on creating new instance of " + table.entityClass, e);
        }
        for (JpaField jpaField : table.getAllColumns()) {
            try {
                jpaField.field.set(instance, rs.getObject(jpaField.column));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Exception on set value to the field " + jpaField.field, e);
            }
        }
        return instance;
    }

    public JpaRowMapper(Class<?> entityClass) {
        this(new JpaTable(entityClass));
    }

    JpaRowMapper(JpaTable table) {
        this.table = table;
    }
}
