package ru.bp.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;

class JpaTable<T> {
    final String name;
    final Class<T> entityClass;
    final List<JpaField> primaryKeys = new ArrayList<>();
    final List<JpaField> columns = new ArrayList<>();

    List<JpaField> getAllColumns() {
        List<JpaField> all = new ArrayList<>();
        all.addAll(primaryKeys);
        all.addAll(columns);
        return all;
    }

    JpaTable(Class<T> entityClass) {
        this.entityClass = entityClass;
        name = readTableName(entityClass);
        readColumns(entityClass);

        assert StringUtils.isNotBlank(this.name);
    }

    private String readTableName(Class<T> entityClass) {
        if (entityClass.getAnnotation(Table.class) != null) {
            return entityClass.getAnnotation(Table.class).name();
        } else {
            return entityClass.getSimpleName();
        }
    }

    private void readColumns(Class<T> entityClass) {
        List<Field> fields = getFields(entityClass);
        for (Field field : fields) {
            if (!JpaField.isJpa(field)) {
                continue;
            }
            JpaField column = new JpaField(field);
            if (column.isPk()) {
                primaryKeys.add(column);
            } else {
                columns.add(column);
            }
        }
    }

    private List<Field> getFields(Class<?> entityClass) {
        List<Field> fields = new ArrayList<>(Arrays.asList(entityClass.getDeclaredFields()));
        Class<?> parent = entityClass.getSuperclass();
        if (parent.equals(Object.class)) {
            return fields;
        }
        fields.addAll(getFields(parent));
        return fields;
    }
}
