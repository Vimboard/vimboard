package com.github.vimboard.utils;

import static org.junit.Assert.*;

public class ArrayCheck<T> {

    public interface ItemCheck {

        void checkItem(Object item);
    }

    public static class Item {

        private final Object[] array;
        private final int index;

        private Item(Object[] array, int index) {
            this.array = array;
            this.index = index;
        }

        public boolean end() {
            assertEquals("Array end not reached,", array.length, index);
            return true;
        }

        public Item item(ItemCheck itemCheck) {
            assertTrue("Array index out of bounds", index < array.length);
            itemCheck.checkItem(array[index]);
            return new Item(array, index + 1);
        }
    }

    private ArrayCheck() {}

    public static Item checkArray(Object object, Class clazz, int length) {
        assertNotNull("Null object", object);
        assertTrue("Ivalid object type <" + object.getClass().getName() + ">",
                clazz.isInstance(object));
        Object[] array = (Object[]) object;
        assertEquals("Invalid array length,", array.length, length);
        return new Item(array, 0);
    }
}
