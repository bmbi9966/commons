package net.dongliu.commons.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Util class for method
 */
public class Methods {

    /**
     * If is static method
     *
     * @param method the method
     * @return true if is static method
     */
    public static boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    /**
     * If is private method
     *
     * @param method the method
     * @return true if is private method
     */
    public static boolean isPrivate(Method method) {
        return Modifier.isPrivate(method.getModifiers());
    }

    /**
     * If is public method
     *
     * @param method the method
     * @return true if is public method
     */
    public static boolean isPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    /**
     * If is protected method
     *
     * @param method the method
     * @return true if is protected method
     */
    public static boolean isProtected(Method method) {
        return Modifier.isProtected(method.getModifiers());
    }

    /**
     * If is abstract method
     *
     * @param method the method
     * @return true if is abstract method
     */
    public static boolean isAbstract(Method method) {
        return Modifier.isAbstract(method.getModifiers());
    }

    /**
     * If is final method
     *
     * @param method the method
     * @return true if is final method
     */
    public static boolean isFinal(Method method) {
        return Modifier.isFinal(method.getModifiers());
    }

    /**
     * If is synchronized method
     *
     * @param method the method
     * @return true if is synchronized method
     */
    public static boolean isSynchronized(Method method) {
        return Modifier.isSynchronized(method.getModifiers());
    }

    /**
     * If is native method
     *
     * @param method the method
     * @return true if is native method
     */
    public static boolean isNative(Method method) {
        return Modifier.isNative(method.getModifiers());
    }
}
