package net.dongliu.commons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Utils for field.
 */
public class Fields {

    /**
     * If is static field
     *
     * @param field the field
     * @return true if is static field
     */
    public static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * If is transient field
     *
     * @param field the field
     * @return true if is transient field
     */
    public static boolean isTransient(Field field) {
        return Modifier.isTransient(field.getModifiers());
    }

    /**
     * If is private field
     *
     * @param field the field
     * @return true if is private field
     */
    public static boolean isPrivate(Field field) {
        return Modifier.isPrivate(field.getModifiers());
    }

    /**
     * If is public field
     *
     * @param field the field
     * @return true if is public field
     */
    public static boolean isPublic(Field field) {
        return Modifier.isPublic(field.getModifiers());
    }

    /**
     * If is protected field
     *
     * @param field the field
     * @return true if is protected field
     */
    public static boolean isProtected(Field field) {
        return Modifier.isProtected(field.getModifiers());
    }

    /**
     * If is final field
     *
     * @param field the field
     * @return true if is final field
     */
    public static boolean isFinal(Field field) {
        return Modifier.isFinal(field.getModifiers());
    }


    /**
     * If is volatile field
     *
     * @param field the field
     * @return true if is volatile field
     */
    public static boolean isVolatile(Field field) {
        return Modifier.isVolatile(field.getModifiers());
    }
}
