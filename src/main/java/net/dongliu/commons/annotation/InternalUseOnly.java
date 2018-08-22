package net.dongliu.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Annotate that this Class/Methods/Package is only for internal implementation,
 * and should not called by code of different module.
 */
@Documented
@Retention(value = CLASS)
@Target(value = {TYPE, CONSTRUCTOR, METHOD, PACKAGE})
public @interface InternalUseOnly {
}
