package net.dongliu.commons;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Beans {

    /**
     * Bean value to string, with default format BeanName{field=value, field2=value2, ...}.
     */
    public static String toString(Object bean) {
        if (bean == null) {
            return "null";
        }
        Class<?> cls = bean.getClass();
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(cls);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        StringBuilder sb = new StringBuilder();
        sb.append(cls.getSimpleName()).append('{');
        boolean flag = false;
        for (PropertyDescriptor descriptor : descriptors) {
            Method getter = descriptor.getReadMethod();
            if (getter == null) {
                continue;
            }
            String name = descriptor.getName();
            if (name.equals("class")) {
                continue;
            }
            try {
                sb.append(name).append('=').append(getter.invoke(bean)).append(", ");
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            if (!flag) {
                flag = true;
            }
        }
        if (flag) {
            sb.setLength(sb.length() - ", ".length());
        }
        sb.append('}');
        return sb.toString();
    }
}
