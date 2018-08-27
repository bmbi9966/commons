package net.dongliu.commons.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Utils method for reflect Proxy
 */
public class Proxies {

    /**
     * Create Proxy form interface. This is a convenient alias for {@link Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)}
     *
     * @param cls     the interface class
     * @param handler the InvocationHandler
     * @param <T>     the interface type
     * @return new instance implement interface
     */
    @SuppressWarnings("unchecked")
    public static <T> T newProxy(Class<T> cls, InvocationHandler handler) {
        if (!Classes.isInterface(cls)) {
            throw new IllegalArgumentException("not interface class");
        }
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls}, handler);
    }
}
