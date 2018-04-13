package net.dongliu.commons.reflection;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

public class ReflectsTest {

    @Test
    public void getAllMemberFields() {
        assertTrue(Reflects.getAllMemberFields(int.class).isEmpty());
        List<Field> IntegerFields = Reflects.getAllMemberFields(Integer.class);
        assertEquals(1, IntegerFields.size());
        assertEquals("value", IntegerFields.get(0).getName());
    }
}