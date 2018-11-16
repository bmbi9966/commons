package net.dongliu.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Annotate the method parameter, method return value, class field, local var, and generic type should not be null.
 * This annotation can be used on all locations a type can be used.
 * <pre>
 * {@code @NonNull String method(@NonNull param); // parameter and return type. }
 * {@code @NonNull String s = null; // local var }
 * {@code List<@NonNull String> list = new ArrayList<>(); //generic type }
 * {@code void method(@NonNull String... params); //the element in params array can not be null}
 * {@code void method(String @NonNull... params); //the params array can not be null}
 * {@code void method(@NonNull String[] params); //the element in params array can not be null}
 * {@code void method(String @NonNull[] params); //the params array can not be null}
 * </pre>
 */
@Documented
@Retention(value = CLASS)
@Target(value = {TYPE_USE})
public @interface NonNull {

}
