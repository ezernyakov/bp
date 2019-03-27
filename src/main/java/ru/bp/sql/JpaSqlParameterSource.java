package ru.bp.sql;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * Ассоциативный массив параметров sql запроса составленный из значений полей jpa-entity.
 * <p/>
 * В качестве названия параметров берутся названия столбцов таблицы, соответствующей jpa-entity.
 *
 */
public class JpaSqlParameterSource implements SqlParameterSource {

    private final MapSqlParameterSource parameterMap;

    @Override
    public boolean hasValue(String paramName) {
        return parameterMap.hasValue(paramName);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        return parameterMap.getValue(paramName);
    }

    @Override
    public int getSqlType(String paramName) {
        return parameterMap.getSqlType(paramName);
    }

    public JpaSqlParameterSource(Object entity) throws IllegalAccessException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity can not be null");
        }
        this.parameterMap = new MapSqlParameterSource();
        JpaTable<?> jpaTable = new JpaTable(entity.getClass());
        for (JpaField jpaField : jpaTable.getAllColumns()) {
            Object value = jpaField.field.get(entity);
            parameterMap.addValue(jpaField.column, value);
        }
    }
}
