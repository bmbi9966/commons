package net.dongliu.commons.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

/**
 * Utils method for Class reflection.
 */
public class Classes {

    /**
     * Method for suppress generic class type cast warning. Usage:
     * <pre>
     * Class&lg;List&lg;String&gt;&gt; cls = Classes.cast(List.class);
     * </pre>
     *
     * @param cls the class
     * @param <T> the class type
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> cast(Class<? super T> cls) {
        return (Class<T>) cls;
    }

    /**
     * Get all non-static, non-Synthetic fields, declared in this class and all it's parent classes.
     * If sub class override parent class's filed, will only return sub class's field.
     *
     * @param cls the class
     * @return list of fields
     */
    public static List<Field> getAllMemberFields(Class<?> cls) {
        requireNonNull(cls);
        Set<String> fieldNames = new HashSet<>();
        List<Field> list = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                continue;
            }
            fieldNames.add(field.getName());
            list.add(field);
        }
        Class<?> superclass = cls.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            List<Field> superFields = getAllMemberFields(superclass);
            for (Field field : superFields) {
                String name = field.getName();
                if (!fieldNames.contains(name)) {
                    fieldNames.add(name);
                    list.add(field);
                }
            }
        }
        return unmodifiableList(list);
    }

    /**
     * Use Reflection to see if class exists, using context classloader of current thread.
     * The class will not be initialized.
     *
     * @param className the full class name
     * @return true if class exists.
     */
    public static boolean exists(String className) {
        return exists(className, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Use Reflection to see if class exists, using specified classloader.
     * The class will not be initialized.
     *
     * @param className the full class name
     * @return true if class exists.
     */
    public static boolean exists(String className, ClassLoader classLoader) {
        requireNonNull(className);
        requireNonNull(classLoader);
        try {
            Class.forName(className, false, classLoader);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * If class has specified method.
     *
     * @param cls            the class
     * @param methodName     the method name
     * @param parameterTypes the method parameter types
     * @return if method exists
     */
    public static boolean hasMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) {
        requireNonNull(cls);
        requireNonNull(methodName);
        try {
            cls.getMethod(methodName, parameterTypes);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private static class ClassSetHolder {
        private static final Set<Class<?>> wrapperClasses = Set.of(
                Byte.class, Short.class, Integer.class, Long.class,
                Float.class, Double.class, Boolean.class, Character.class
        );
    }

    /**
     * If class is primitive wrapper class(Integer, Boolean, etc).
     *
     * @param cls the class
     * @return true if is primitive wrapper class
     */
    public static boolean isPrimitiveWrapper(Class<?> cls) {
        return ClassSetHolder.wrapperClasses.contains(requireNonNull(cls));
    }

    /**
     * If is private class
     *
     * @param cls the class
     * @return true if is private class
     */
    public static boolean isPrivate(Class<?> cls) {
        return Modifier.isPrivate(cls.getModifiers());
    }

    /**
     * If is public class
     *
     * @param cls the class
     * @return true if is public class
     */
    public static boolean isPublic(Class<?> cls) {
        return Modifier.isPublic(cls.getModifiers());
    }

    /**
     * If is protected class
     *
     * @param cls the class
     * @return true if is protected class
     */
    public static boolean isProtected(Class<?> cls) {
        return Modifier.isProtected(cls.getModifiers());
    }

    /**
     * If is abstract class
     *
     * @param cls the class
     * @return true if is abstract class
     */
    public static boolean isAbstract(Class<?> cls) {
        return Modifier.isAbstract(cls.getModifiers());
    }

    /**
     * If is interface class
     *
     * @param cls the class
     * @return true if is interface
     */
    public static boolean isInterface(Class<?> cls) {
        return Modifier.isInterface(cls.getModifiers());
    }

    /**
     * If is final class
     *
     * @param cls the class
     * @return true if is final class
     */
    public static boolean isFinal(Class<?> cls) {
        return Modifier.isFinal(cls.getModifiers());
    }
}
