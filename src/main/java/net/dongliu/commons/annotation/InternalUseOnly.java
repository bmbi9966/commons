package net.dongliu.commons.annotation;

import java.lang.annotation.*;

/**
 * Annotation to mark only for internal use.
 */
@Documented
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.PACKAGE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface InternalUseOnly {
}
