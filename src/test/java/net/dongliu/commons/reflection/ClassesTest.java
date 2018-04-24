package net.dongliu.commons.reflection;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClassesTest {

    @Test
    public void getAllMemberFields() {
        assertTrue(Classes.getAllMemberFields(int.class).isEmpty());
        List<Field> IntegerFields = Classes.getAllMemberFields(Integer.class);
        assertEquals(1, IntegerFields.size());
        assertEquals("value", IntegerFields.get(0).getName());
    }

    @Test
    public void exists() {
        assertTrue(Classes.exists("java.lang.String"));
        assertTrue(Classes.exists("net.dongliu.commons.collection.Iterables"));
        assertFalse(Classes.exists("java.lang.String2"));
    }

    @Test
    public void hasMethod() {
        assertTrue(Classes.hasMethod(String.class, "compareToIgnoreCase", String.class));
        assertFalse(Classes.hasMethod(String.class, "compareToIgnoreCase1", String.class));
    }

    @Test
    public void isPrimitiveWrapper() {
        assertTrue(Classes.isPrimitiveWrapper(Integer.class));
        assertFalse(Classes.isPrimitiveWrapper(int.class));
        assertFalse(Classes.isPrimitiveWrapper(String.class));
    }
}