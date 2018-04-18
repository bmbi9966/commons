package net.dongliu.commons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableList;

public class Classes {
    /**
     * Get all non-static, non-Synthetic fields, declared in this class and all it's parent classes.
     * If sub class override parent class's filed, will only return sub class's field.
     *
     * @param cls the class
     * @return list of fields
     */
    public static List<Field> getAllMemberFields(Class<?> cls) {

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
     * Use Reflection to see if class exists, using classloader which load This class.
     * The class will not be initialized.
     *
     * @param className the full class name
     * @return true if class exists.
     */
    public static boolean exists(String className) {
        // we cannot get caller's class loader... so just use class load which load this class as default.
        return exists(className, Classes.class.getClassLoader());
    }

    /**
     * Use Reflection to see if class exists, using specified classloader.
     * The class will not be initialized.
     *
     * @param className the full class name
     * @return true if class exists.
     */
    public static boolean exists(String className, ClassLoader classLoader) {
        try {
            Class.forName(className, false, classLoader);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
