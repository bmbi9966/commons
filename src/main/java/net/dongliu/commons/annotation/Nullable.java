package net.dongliu.commons.annotation;

import java.lang.annotation.*;

/**
 * Annotation to mark type as Nullable
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.CLASS)
public @interface Nullable {
}
