package ru.bp.sql;

import javax.sql.DataSource;

/**
 * Класс для упрощения взаимодействия с базой данных.
 *
 */

public class DataBase {
    final DataSource ds;

    /**
     * Выполняет замену JPA-entity в базе.
     *
     * @param entity
     *            jpa-entity которую необходимо вставить в базу.
     * @return количество измененных строк.
     * @throws IllegalAccessException
     */
    public Replace replace(Object entity) throws IllegalAccessException {
        final JpaSqlParameterSource parameterSource = new JpaSqlParameterSource(entity);
        final Sql sql = Sql.generateFor(entity.getClass());
        return new Replace(ds, sql, parameterSource);
    }

    /**
     * Выполняет удаление JPA-entity в базе
     * @param entity jpa-entity для удаления из базы
     * @return количество удаленных строк
     * @throws IllegalAccessException
     */
    public Delete delete(Object entity) throws IllegalAccessException {
        final JpaSqlParameterSource parameterSource = new JpaSqlParameterSource(entity);
        final Sql sql = Sql.generateFor(entity.getClass());
        return new Delete(ds, sql, parameterSource);
    }

    /**
     * Выполняет выборку по JPA-entity из базы
     * @param entity jpa-entity для выборки из базы
     * @return List<Map<String,Object>> с результатами выборки
     * @throws IllegalAccessException
     */
    public Select select(Object entity) throws IllegalAccessException {
        final JpaSqlParameterSource parameterSource = new JpaSqlParameterSource(entity);
        final Sql sql = Sql.generateFor(entity.getClass());
        return new Select(ds, sql, parameterSource);
    }

    public DataBase(DataSource ds) {
        this.ds = ds;
    }
}
