package ru.bp.sql;

import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

/**
 * Инструмент для гененрирования sql-запросов по аннотированной jpa-entity.
 *
 */
public class Sql<T> {

    JpaTable<T> table;
    private Class<T> entityClass;

    public static <E> Sql generateFor(Class<E> entityClass) {
        return new Sql<E>(entityClass);
    }

    public SqlWithWhereClause select() {
        StringBuilder sql = new StringBuilder("select ");
        for (JpaField jpaField : table.getAllColumns()) {
            sql.append(jpaField.column).append(", ");
        }
        sql.delete(sql.length() - ", ".length(), sql.length());
        sql.append(" from ").append(table.name);
        return new SqlWithWhereClause(sql.toString(), table);
    }

    public SqlWithWhereClause select(String... columns) {
        StringBuilder sql = new StringBuilder("select ");
        for (String column : columns) {
            sql.append(column).append(", ");
        }
        sql.delete(sql.length() - ", ".length(), sql.length());
        sql.append(" from ").append(table.name);
        return new SqlWithWhereClause(sql.toString(), table);
    }

    public RowMapper createRowMapper() {
        return new JpaRowMapper(table);
    }

    public JpaSqlParameterSource createParameterSource(T object) throws IllegalAccessException {
        return new JpaSqlParameterSource(object);
    }

    public SqlWithWhereClause selectCount() {
        StringBuilder sql = new StringBuilder("select count(*) ");
        sql.append(" from ").append(table.name);
        return new SqlWithWhereClause(sql.toString(), table);
    }

    public SqlWithWhereClause delete() {
        StringBuilder sql = new StringBuilder("delete from ").append(table.name);
        return new SqlWithWhereClause(sql.toString(), table);
    }

    public SqlWithWhereClause update() {
        StringBuilder sql = new StringBuilder("update ").append(table.name).append(" set ");

        List<JpaField> allColumns = new ArrayList<>();
        allColumns.addAll(table.primaryKeys);
        allColumns.addAll(table.columns);
        for (JpaField jpaField : allColumns) {
            if (jpaField.isPk()) {
                continue;
            }
            sql.append(jpaField.column).append(" = ").append(":" + jpaField.column).append(", ");
        }
        sql.delete(sql.length() - ", ".length(), sql.length());
        return new SqlWithWhereClause(sql.toString(), table);
    }

    public String insert() {
        StringBuilder sql = new StringBuilder("insert into ").append(table.name);
        StringBuilder columns = new StringBuilder(" ( ");
        StringBuilder values = new StringBuilder(" values (");

        List<JpaField> allColumns = new ArrayList<>();
        allColumns.addAll(table.primaryKeys);
        allColumns.addAll(table.columns);
        for (JpaField jpaField : allColumns) {
            columns.append(jpaField.column).append(", ");
            if (jpaField.isGenerated()) {
                values.append("nextval('").append(jpaField.getSequence()).append("'), ");
            } else {
                values.append(":" + jpaField.column).append(", ");
            }
        }
        columns.delete(columns.length() - ", ".length(), columns.length()).append(" ) ");
        values.delete(values.length() - ", ".length(), values.length()).append(" ) ");
        sql.append(columns).append(values);

        return sql.toString();
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    private Sql(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.table = new JpaTable<T>(entityClass);
    }

    public static class CreateNewEntityException extends DataAccessException {

        public CreateNewEntityException(String msg) {
            super(msg);
        }

        public CreateNewEntityException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
