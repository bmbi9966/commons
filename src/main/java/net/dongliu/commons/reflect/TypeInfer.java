package net.dongliu.commons.reflect;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * For Representing a generic type {@code T}.
 */
public class TypeInfer<T> {
    final Type type;

    /**
     * Constructs a new type literal. Derives represented class from type
     * parameter.
     *
     * <p>Clients create an empty anonymous subclass. Doing so embeds the type
     * parameter in the anonymous class's type hierarchy so we can reconstitute it
     * at runtime despite erasure.
     */
    protected TypeInfer() {
        var superType = (ParameterizedType) getClass().getGenericSuperclass();
        this.type = superType.getActualTypeArguments()[0];
    }

    /**
     * Gets underlying {@code Type} instance.
     */
    public final Type getType() {
        return type;
    }

}
