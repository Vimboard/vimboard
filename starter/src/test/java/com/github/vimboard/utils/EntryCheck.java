package com.github.vimboard.utils;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntryCheck {

    public interface ValueCheck {

        void checkValue(Object value);
    }

    public static class Value {

        private final Object value;

        private Value(Object value) {
            this.value = value;
        }

        public boolean value(ValueCheck valueCheck) {
            valueCheck.checkValue(value);
            return true;
        }
    }

    private EntryCheck() {}

    public static Value checkEntry(Object object, Object key) {
        assertTrue("Ivalid object type <" + object.getClass().getName() + ">",
                object instanceof Map.Entry);
        Map.Entry entry = (Map.Entry) object;
        assertEquals("Ivalid key,", key, entry.getKey());
        return new Value(entry.getValue());
    }

    public static boolean checkEntry(Object object, Object key, Object value) {
        assertTrue("Ivalid object type <" + object.getClass().getName() + ">",
                object instanceof Map.Entry);
        Map.Entry entry = (Map.Entry) object;
        assertEquals("Ivalid key,", key, entry.getKey());
        assertEquals("Ivalid value,", value, entry.getValue());
        return true;
    }
}
