package net.dongliu.commons.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TypeInferTest {

    @Test
    void getType() {
        assertEquals(String.class, new TypeInfer<String>(){}.getType());
        var type = (ParameterizedType)new TypeInfer<List<String>>(){}.getType();
        assertEquals(List.class, type.getRawType());
    }
}