package ru.bp.sql;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import javax.persistence.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Описывает поле JPA сущности.
 *
 */
public class JpaField {

    public final Field field;
    public final String column;

    public static boolean isJpa(Field field) {
        return !(isStatic(field) || isTransient(field) || isEmbedded(field) || containManyAssociation(field));
    }

    public JpaField(Field field) {
        this.field = field;
        field.setAccessible(true);
        this.column = getColumnName(field);
    }

    private String getColumnName(Field field) {
        String name;
        Column annotation = field.getAnnotation(Column.class);
        if (annotation != null && StringUtils.isNotEmpty(annotation.name())) {
            name = annotation.name();
        } else {
            name = field.getName();
        }
        return name;
    }

    public boolean isPk() {
        return field.getAnnotation(Id.class) != null;
    }

    public boolean isGenerated() {
        return field.getAnnotation(GeneratedValue.class) != null;
    }

    public String getSequence() {
        GeneratedValue generated = field.getAnnotation(GeneratedValue.class);
        if (generated == null) {
            return null;
        }
        switch (generated.strategy()) {
            case AUTO:
                return "hibernate_sequence";
            case SEQUENCE:
                if (!StringUtils.isEmpty(generated.generator())) {
                    throw new sun.reflect.generics.reflectiveObjects.NotImplementedException();
                }
                return "hibernate_sequence";
            default:
                throw new sun.reflect.generics.reflectiveObjects.NotImplementedException();
        }
    }

    private static boolean isTransient(Field field) {
        return field.getAnnotation(Transient.class) != null;
    }

    private static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    private static boolean isEmbedded(Field field) {
        return field.getAnnotation(Embedded.class) != null;
    }

    private static boolean containManyAssociation(Field field) {
        if (field.getAnnotation(OneToOne.class) != null) {
            return true;
        }
        if (field.getAnnotation(OneToMany.class) != null) {
            return true;
        }
        if (field.getAnnotation(ManyToOne.class) != null) {
            return true;
        }
        if (field.getAnnotation(ManyToMany.class) != null) {
            return true;
        }
        return false;
    }
}
