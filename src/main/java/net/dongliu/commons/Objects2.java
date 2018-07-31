package net.dongliu.commons;


import net.dongliu.commons.concurrent.ClassProcessorLoader;
import net.dongliu.commons.reflection.Classes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Jdk already has one Objects class, So named as Objects2
 */
public class Objects2 {

    /**
     * Elvis operator.
     * If value1 is not null, use return value1; else return values2.
     *
     * @param <T> the value type
     * @return non-null value
     * @throws NullPointerException if value2 is null
     */
    @NotNull
    public static <T> T elvis(@Nullable T value1, T value2) {
        if (value1 != null) {
            return value1;
        }
        return requireNonNull(value2);
    }

    /**
     * Elvis operator.
     * If value1 is not null, use return value1; else use value calculate by supplier.
     *
     * @param supplier to calculate non-null value
     * @param <T>      the value type
     * @return non-null value
     * @throws NullPointerException if supplier is null or produce null value
     */
    @NotNull
    public static <T> T elvis(@Nullable T value1, Supplier<T> supplier) {
        if (value1 != null) {
            return value1;
        }
        return requireNonNull(supplier.get());
    }

    /**
     * Get a ToStringHelper to convert specific type object to string.
     */
    @NotNull
    public static ToStringHelper toStringHelper(Class<?> cls) {
        return new ToStringHelper(requireNonNull(cls));
    }

    private static class ToStringCacheHolder {
        private static final ClassProcessorLoader<Object, ToStringHelper> cache = ClassProcessorLoader.of(
                Objects2::toStringHelper
        );
    }

    /**
     * Convert object to string, by concat each filed name and value, using reflection.
     *
     * <p>
     * Implementation Details: This method use a internal weak ToStringHelper cache to speed up.
     * </p>
     *
     * @param value the object to convert to string
     * @return string represent of value
     */
    public static String toString(@Nullable Object value) {
        if (value == null) {
            return "null";
        }
        ToStringHelper toStringHelper = ToStringCacheHolder.cache.get(value.getClass());
        return toStringHelper.toString(value);
    }

    /**
     * A class to convert object to string, by concat each filed name and value, using reflection.
     * This class is immutable, use should reuse ToStringHelper for all instances of one type.
     */
    public static class ToStringHelper {
        private final Class<?> cls;
        private final boolean hasToStringMethod;
        private final List<Field> memberFields;
        private final String className;

        protected ToStringHelper(Class<?> cls) {
            this.cls = cls;
            this.hasToStringMethod = classHasStringMethod(cls);
            this.memberFields = Classes.getAllMemberFields(cls);
            String className;
            if (cls.isAnonymousClass()) {
                className = Strings.subStringAfterLast(cls.getName(), ".");
            } else {
                className = cls.getSimpleName();
            }
            this.className = className;
        }


        /**
         * Convert object to string.
         * If Object has override toString method, will can this; else, will build string by concat
         * all member fields of this object.
         * Array field of object, will convert to string using Arrays.toString;
         * otherwise, will use call field value's toString method.
         *
         * @param value the value
         * @return the string representation of this object value. If object is null, return "null"
         */
        public String toString(@Nullable Object value) {
            if (value == null) {
                return "null";
            }
            if (!this.cls.equals(value.getClass())) {
                throw new IllegalArgumentException("value type " + value.getClass().getName() + " not match with ToStringHelper");
            }

            if (hasToStringMethod) {
                return value.toString();
            }

            if (cls.isArray()) {
                return arrayToString(value);
            }

            StringBuilder sb = new StringBuilder();
            sb.append(className).append('{');
            for (Field field : memberFields) {
                field.setAccessible(true);
                sb.append(field.getName()).append('=');

                Class<?> type = field.getType();
                if (type.isPrimitive()) {
                    try {
                        appendPrimitiveField(sb, field, value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Object fieldValue;
                    try {
                        fieldValue = field.get(value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    if (fieldValue == null) {
                        sb.append("null");
                    } else if (fieldValue.getClass().isArray()) {
                        sb.append(arrayToString(fieldValue));
                    } else {
                        sb.append(fieldValue.toString());
                    }
                }
                sb.append(", ");
            }
            if (!memberFields.isEmpty()) {
                sb.setLength(sb.length() - 2);
            }
            sb.append('}');
            return sb.toString();
        }

        private boolean classHasStringMethod(Class<?> cls) {
            try {
                Method method = cls.getDeclaredMethod("toString");
                // may have strange generated method return different type?
                return method.getReturnType().equals(String.class);
            } catch (NoSuchMethodException e) {
                return false;
            }
        }

        private void appendPrimitiveField(StringBuilder sb, Field field, Object value) throws IllegalAccessException {
            Class<?> type = field.getType();
            if (type == int.class) {
                sb.append(field.getInt(value));
            } else if (type == long.class) {
                sb.append(field.getLong(value));
            } else if (type == short.class) {
                sb.append(field.getShort(value));
            } else if (type == boolean.class) {
                sb.append(field.getBoolean(value));
            } else if (type == float.class) {
                sb.append(field.getFloat(value));
            } else if (type == double.class) {
                sb.append(field.getDouble(value));
            } else if (type == byte.class) {
                sb.append(field.getByte(value));
            } else if (type == char.class) {
                sb.append(field.getChar(value));
            } else {
                throw new RuntimeException("not primitive type: " + type.getName());
            }
        }

        private String arrayToString(Object value) {
            if (value instanceof boolean[]) {
                return Arrays.toString((boolean[]) value);
            } else if (value instanceof char[]) {
                return Arrays.toString((char[]) value);
            } else if (value instanceof byte[]) {
                return Arrays.toString((byte[]) value);
            } else if (value instanceof short[]) {
                return Arrays.toString((short[]) value);
            } else if (value instanceof int[]) {
                return Arrays.toString((int[]) value);
            } else if (value instanceof long[]) {
                return Arrays.toString((long[]) value);
            } else if (value instanceof float[]) {
                return Arrays.toString((float[]) value);
            } else if (value instanceof double[]) {
                return Arrays.toString((double[]) value);
            } else {
                return Arrays.toString((Object[]) value);
            }
        }
    }


}
