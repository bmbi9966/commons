package net.dongliu.commons;


import net.dongliu.commons.collection.Lists;
import net.dongliu.commons.reflect.Classes;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Jdk already has one Objects class, So named as Objects2
 */
public class Objects2 {

    /**
     * Method for suppress generic class type cast warning. Usage:
     * <pre>
     * var list = Objects2.<List<String>>cast(value);
     * </pre>
     *
     * @param <T> the class type
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object value) {
        return (T) value;
    }


    /**
     * Elvis operator.
     * If value1 is not null, use return value1; else return values2.
     *
     * @param <T> the value type
     * @return non-null value
     * @throws NullPointerException if value2 is null
     */
    public static <T> T elvis(@Nullable T value1, T value2) {
        if (value1 != null) {
            return value1;
        }
        return requireNonNull(value2);
    }


    /**
     * Convenient method for manipulating object.
     * <pre>
     * Pojo pojo = runWith(new Pojo(), p -> {p.setX(x); p.setY(y);})
     * </pre>
     */
    public static <T> T runWith(T value, Consumer<T> consumer) {
        consumer.accept(value);
        return value;
    }

    /**
     * Elvis operator.
     * If value1 is not null, use return value1; else use value calculate by supplier
     *
     * @param supplier to calculate non-null value
     * @param <T>      the value type
     * @return non-null value
     * @throws NullPointerException if supplier is null or produce null value
     */
    public static <T> T elvis(@Nullable T value1, Supplier<T> supplier) {
        if (value1 != null) {
            return value1;
        }
        return requireNonNull(supplier.get());
    }

    /**
     * Get a ToStringHelper to convert specific type object to string.
     */
    @Deprecated
    public static ToStringHelper toStringHelper(Class<?> cls) {
        return new ToStringHelper(requireNonNull(cls));
    }

    @Deprecated
    private static class ToStringCacheHolder {
        private static final ClassValue<ToStringHelper> cache = new ClassValue<>() {
            @Override
            protected ToStringHelper computeValue(Class<?> type) {
                return new ToStringHelper(requireNonNull(type));
            }
        };
    }

    /**
     * Convert object to string, by concat each filed name and value, using reflection.
     *
     * <p>
     * Implementation Details: This method use a internal weak ToStringHelper cache to boost performance.
     * </p>
     *
     * @param value the object to convert to string
     * @return string represent of value
     */
    @Deprecated
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
    @Deprecated
    public static class ToStringHelper {
        private final Class<?> cls;
        private final List<Field> memberFields;
        private final String className;

        protected ToStringHelper(Class<?> cls) {
            this.cls = cls;
            List<Field> memberFields = Classes.getAllMemberFields(cls);
            for (Field field : memberFields) {
                field.setAccessible(true);
            }
            this.memberFields = memberFields;
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
         * @param obj the value
         * @return the string representation of this object value. If object is null, return "null"
         */
        public String toString(@Nullable Object obj) {
            if (obj == null) {
                return "null";
            }
            if (!this.cls.equals(obj.getClass())) {
                throw new IllegalArgumentException("value type " + obj.getClass().getName() + " not match with ToStringHelper");
            }

            if (cls.isArray()) {
                return arrayToString(obj);
            }

            StringBuilder sb = new StringBuilder();
            sb.append(className).append('{');
            Lists.forEach(memberFields, (field, last) -> {
                appendFiledKeyAndValue(sb, obj, field);
                if (!last) {
                    sb.append(", ");
                }
            });
            sb.append('}');
            return sb.toString();
        }

        private void appendFiledKeyAndValue(StringBuilder sb, Object obj, Field field) {
            sb.append(field.getName()).append('=');

            Class<?> type = field.getType();
            if (type.isPrimitive()) {
                try {
                    appendPrimitiveField(sb, field, obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Object fieldValue;
                try {
                    fieldValue = field.get(obj);
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
