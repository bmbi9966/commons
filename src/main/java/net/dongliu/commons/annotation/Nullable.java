package net.dongliu.commons.annotation;

import java.lang.annotation.*;

/**
 * Annotation, to mark the type can hold null value.
 * When this annotation is applied to a method it applies to the method return value.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface Nullable {
}
