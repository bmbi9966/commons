package net.dongliu.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Annotate the method parameter, method return value, class field, local var, and generic type should not be null.
 * This annotation can be used on all locations a type can be used.
 * <p>
 * {@code
 *
 * @NonNull String method(@NonNull param); // parameter and return type.
 * @NonNull String s = null; // local var
 * List<@NonNull String> list = new ArrayList<>(); //generic type
 * }
 * <pre></pre>
 */
@Documented
@Retention(value = CLASS)
@Target(value = {TYPE_USE})
public @interface NonNull {

}
