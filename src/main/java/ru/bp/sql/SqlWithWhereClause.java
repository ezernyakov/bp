package ru.bp.sql;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlWithWhereClause {
    private JpaTable<?> table;
    private String sql;

    public String byPk() {
        FluentIterable<String> pkColumns = FluentIterable.from(table.primaryKeys).transform(new Function<JpaField, String>() {
            @Override
            public String apply(JpaField input) {
                return input.column;
            }
        });
        return byColumns(pkColumns.toList());
    }

    public String forAll() {
        return sql;
    }

    public String byNotNullFields(Object entity) throws IllegalAccessException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity can not be null");
        }
        if (!entity.getClass().equals(table.entityClass)) {
            throw new IllegalArgumentException(String.format("Wrong entity class. Expected %s, but was %s",
                    table.entityClass.getCanonicalName(),
                    entity.getClass().getCanonicalName()));
        }
        List<String> notNullColumns = new ArrayList<>();
        for (JpaField jpaField : table.getAllColumns()) {
            if (jpaField.field.get(entity) == null) {
                continue;
            }
            notNullColumns.add(jpaField.column);
        }
        return byColumns(notNullColumns);
    }

    public String byColumns(String... columnsNames) {
        return byColumns(Arrays.asList(columnsNames));
    }

    public String byColumns(List<String> columnsNames) {
        if (columnsNames ==  null || columnsNames.isEmpty()) {
            throw new IllegalArgumentException("For select forAll use method forAll()");
        }
        StringBuilder sql = new StringBuilder(this.sql).append(" where ");
        for (String column : columnsNames) {
            sql.append(column).append(" = ").append(":" + column).append(" and ");
        }
        sql.delete(sql.length() - " and ".length(), sql.length());
        return sql.toString();
    }

    public String where(String whereClause) {
        return sql + " where " + whereClause;
    }

    public String likeColumns(String... columnsNames) {
        return likeColumns(Arrays.asList(columnsNames));
    }

    public String likeColumns(List<String> columnsNames) {
        if (columnsNames ==  null || columnsNames.isEmpty()) {
            throw new IllegalArgumentException("For select forAll use method forAll()");
        }
        StringBuilder sql = new StringBuilder(this.sql).append(" where ");
        for (String column : columnsNames) {
            sql.append(column).append(" LIKE ").append("'%:" + column).append("%' and ");
        }
        sql.delete(sql.length() - " and ".length(), sql.length());
        return sql.toString();
    }

    public String last() {
        FluentIterable<String> pkColumns = FluentIterable.from(table.primaryKeys).transform(new Function<JpaField, String>() {
            @Override
            public String apply(JpaField input) {
                return input.column;
            }
        });

        List<String> columnsNames = pkColumns.toList();
        byColumns(pkColumns.toList());

        return sql + " order by " + columnsNames.get(0) + " desc limit 1";
    }

    SqlWithWhereClause(String sql, JpaTable table) {
        this.table = table;
        this.sql = sql;
    }
}
