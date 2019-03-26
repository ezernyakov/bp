package ru.bp.sql;

import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Выполняет замену записи об объекте по некоторому критерию.
 * Часть DSL для работы с базой.
 *
 */
public class Replace {

    final NamedParameterJdbcTemplate jt;
    final Sql sql;
    final JpaSqlParameterSource parameterSource;

    public Integer byPk() {
        final int count = jt.queryForInt(sql.selectCount().byPk(), parameterSource);
        if (count == 0) {
            return insert();
        } else {
            return jt.update(sql.update().byPk(), parameterSource);
        }
    }

    public Integer byColumns(String... columnsNames) {
        return byColumns(Arrays.asList(columnsNames));
    }

    public Integer byColumns(List<String> columnsNames) {
        final int count = jt.queryForInt(sql.selectCount().byColumns(columnsNames), parameterSource);
        if (count > 0) {
            jt.update(sql.delete().byColumns(columnsNames), parameterSource);
        }
        return insert();
    }

    public Integer where(String whereClause) {
        return null;
    }

    public Replace(DataSource ds, Sql sql, JpaSqlParameterSource parameterSource) {
        this.jt = new NamedParameterJdbcTemplate(ds);
        this.sql = sql;
        this.parameterSource = parameterSource;
    }

    public int insert() {
        return jt.update(sql.insert(), parameterSource);
    }
}
