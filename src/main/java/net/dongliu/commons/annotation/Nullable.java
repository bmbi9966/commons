package net.dongliu.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Annotate the method parameter, method return value, class field, local var, and generic type can be null.
 * This annotation can be used on all locations a type can be used.
 * <pre>
 * {@code @Nullable String method(@Nullable param); // parameter and return type. }
 * {@code @Nullable String s = null; // local var }
 * {@code List<@Nullable String> list; //generic type}
 * {@code void method(@Nullable String... params); //the element in params array can be null}
 * {@code void method(String @Nullable... params); //the params array can be null}
 * {@code void method(@Nullable String[] params); //the element in params array can be null}
 * {@code void method(String @Nullable[] params); //the params array can be null}
 * </pre>
 */
@Documented
@Retention(value = CLASS)
@Target(value = {TYPE_USE})
public @interface Nullable {

}
