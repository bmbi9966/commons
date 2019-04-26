package net.dongliu.commons.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class ClassesTest {

    @Test
    void cast() {
        Class<List<String>> cls = Classes.cast(List.class);
    }

    @Test
    void getAllMemberFields() {
        assertTrue(Classes.getAllMemberFields(int.class).isEmpty());
        List<Field> IntegerFields = Classes.getAllMemberFields(Integer.class);
        assertEquals(1, IntegerFields.size());
        assertEquals("value", IntegerFields.get(0).getName());
    }

    @Test
    void exists() {
        assertTrue(Classes.exists("java.lang.String"));
        assertTrue(Classes.exists("net.dongliu.commons.collection.Iterables"));
        assertFalse(Classes.exists("java.lang.String2"));
    }

    @Test
    void hasMethod() {
        assertTrue(Classes.hasMethod(String.class, "compareToIgnoreCase", String.class));
        assertFalse(Classes.hasMethod(String.class, "compareToIgnoreCase1", String.class));
    }

    @Test
    void isPrimitiveWrapper() {
        assertTrue(Classes.isPrimitiveWrapper(Integer.class));
        assertFalse(Classes.isPrimitiveWrapper(int.class));
        assertFalse(Classes.isPrimitiveWrapper(String.class));
    }

    private static final Class<?> currentClass = Classes.currentClass();

    @Test
    void currentClass() {
        assertEquals(ClassesTest.class, currentClass);
        assertEquals(ClassesTest.class, Classes.currentClass());
        assertEquals(ClassesTest.class, new Supplier<Class<?>>() {
            @Override
            public Class<?> get() {
                return Classes.currentClass();
            }
        }.get());
        assertEquals(InnerClassForTestCurrent.class, new InnerClassForTestCurrent().current());
    }

    private static class InnerClassForTestCurrent {

        private Class<?> current() {
            return Classes.currentClass();
        }
    }

}