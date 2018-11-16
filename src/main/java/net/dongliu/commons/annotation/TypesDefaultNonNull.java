package net.dongliu.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Annotate all parameters, return values, local variables, generic types, etc, in a class or package, is NonNull by default.
 */
@Documented
@Retention(value = CLASS)
@Target(value = {TYPE, PACKAGE})
public @interface TypesDefaultNonNull {

}
