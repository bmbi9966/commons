package net.dongliu.commons;

import org.junit.Test;

import static org.junit.Assert.*;

public class BeansTest {

    @Test
    public void testToString() {
        assertEquals("{i=0}", Beans.toString(new Object() {
            private int i = 0;

            public int getI() {
                return i;
            }
        }));

        Person person = new Person();
        person.setAge(10);
        person.setName("jerry");
        String str = Beans.toString(person);
        assertTrue(str.equals("Person{age=10, name=jerry}") || str.equals("Person{name=jerry, age=10}"));
    }

    private static class Person {
        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}