package ru.bp.sql;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *  Выполняет выборку из базы данных по некоторому критерию
 */
public class Select {

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

    public List<Map<String, Object>> byColumns(String... columnsNames) {
        return byColumns(Arrays.asList(columnsNames));
    }

    public List<Map<String, Object>> byColumns(List<String> columnsNames) {
        return jt.queryForList(sql.select().byColumns(columnsNames), parameterSource);
    }

    public List all() {
        return jt.query(sql.select().forAll(), parameterSource, sql.createRowMapper());
    }

    public Map<String, Object> last() {
        List<Map<String, Object>> rowList = jt.queryForList(sql.select().last(), parameterSource);
        return rowList.isEmpty() ?  null : rowList.get(0);
    }

    public Integer where(String whereClause) {
        return null;
    }

    public Select(DataSource ds, Sql sql, JpaSqlParameterSource parameterSource) {
        this.jt = new NamedParameterJdbcTemplate(ds);
        this.sql = sql;
        this.parameterSource = parameterSource;
    }

}
