package ru.bp.sql;

import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class Delete {
    final NamedParameterJdbcTemplate jt;
    final Sql sql;
    final JpaSqlParameterSource parameterSource;

    public Integer byPk() {
        final int count = jt.queryForInt(sql.selectCount().byPk(), parameterSource);
        if (count == 0) {
            return 0;
        } else {
            return jt.update(sql.delete().byPk(), parameterSource);
        }
    }

    public Integer forAll() {
        final int count = jt.queryForInt(sql.selectCount().forAll(), parameterSource);
        if (count == 0) {
            return 0;
        } else {
            return jt.update(sql.delete().forAll(), parameterSource);
        }
    }

    public Integer byColumns(String... columnsNames) {
        return byColumns(Arrays.asList(columnsNames));
    }

    public Integer byColumns(List<String> columnsNames) {
        final int count = jt.queryForInt(sql.selectCount().byColumns(columnsNames), parameterSource);
        if (count == 0) {
            return 0;
        } else {
            return jt.update(sql.delete().byColumns(columnsNames), parameterSource);
        }
    }

    public Delete(DataSource ds, Sql sql, JpaSqlParameterSource parameterSource) {
        this.jt = new NamedParameterJdbcTemplate(ds);
        this.sql = sql;
        this.parameterSource = parameterSource;
    }
}
