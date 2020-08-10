package com.github.vimboard.config;

import javax.validation.ValidationException;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SettingsBuilder<P, S> {

    private static final String INVALID_BEAN = "Invalid '%s' bean";
    private static final String INVALID_BEAN_METHOD = "Can't find method for '%s' bean poperty";
    private static final String INVALID_BEAN_PROPERTY = "Invalid '%s' bean poperty";

    public interface SettingsBuilderConverter<P, S> {

        Object convert(SettingsBuilder<P, S> reader, Object value);
    }

    final String boardUri;
    final String basePath;
    final Map<String, Object> propertiesMap;
    final Map<String, Object> allSettingsMap;
    final Map<String, Object> settingsMap;
    final S resultSettings;

    public SettingsBuilder(String boardUri, String basePath,
            P properties, S allSettings, S resultSettings) {
        this.boardUri = boardUri;
        this.basePath = basePath;
        propertiesMap = objectToMap(properties);
        allSettingsMap = objectToMap(allSettings);
        this.settingsMap = new HashMap<>();
        this.resultSettings = resultSettings;
    }

    public S build() {
        //final Object result = createSettings();
        final BeanInfo info;
        try {
            info = Introspector.getBeanInfo(resultSettings.getClass());
        } catch (IntrospectionException ex) {
            throw new ValidationException(String.format(INVALID_BEAN,
                    propertyPath(boardUri)));
        }
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            if (pd.getName().equals("class")) {
                continue;
            }
            Method setter = pd.getWriteMethod();
            if (setter == null) {
                throw new ValidationException(String.format(INVALID_BEAN_METHOD,
                        "setter: " + propertyPath(boardUri) + "." + pd.getName()));
            }
            try {
                setter.invoke(resultSettings, settingsMap.get(pd.getName()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ValidationException(String.format(INVALID_BEAN_PROPERTY,
                        propertyPath(boardUri) + "." + pd.getName()));
            }
        }
        return resultSettings;
    }

//    protected abstract Object createSettings();

    public Object put(String fieldName, Object defaultValue) {
        if (propertiesMap == null || propertiesMap.get(fieldName) == null) {
            settingsMap.put(fieldName, (boardUri == null
                    ? defaultValue
                    : allSettingsMap.get(fieldName)));
        } else {
            settingsMap.put(fieldName, propertiesMap.get(fieldName));
        }
        return settingsMap.get(fieldName);
    }

    public Object put(String fieldName, Object defaultValue,
            SettingsBuilderConverter converter) {
        if (propertiesMap == null || propertiesMap.get(fieldName) == null) {
            settingsMap.put(fieldName, (boardUri == null
                    ? defaultValue
                    : allSettingsMap.get(fieldName)));
        } else {
            settingsMap.put(fieldName, converter.convert(
                    this, propertiesMap.get(fieldName)));
        }
        return settingsMap.get(fieldName);
    }

    private Map<String, Object> objectToMap(Object object) {
        if (object == null) {
            return null;
        }
        final Map<String, Object> result = new HashMap<>();
        final BeanInfo info;
        try {
            info = Introspector.getBeanInfo(object.getClass());
        } catch (IntrospectionException ex) {
            throw new ValidationException(String.format(INVALID_BEAN,
                    propertyPath(boardUri)));
        }
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            if (pd.getName().equals("class")) {
                continue;
            }
            Method getter = pd.getReadMethod();
            if (getter == null) {
                throw new ValidationException(String.format(INVALID_BEAN_METHOD,
                        "getter: " + propertyPath(boardUri) + "." + pd.getName()));
            }
            try {
                result.put(pd.getName(), getter.invoke(object));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ValidationException(String.format(INVALID_BEAN_PROPERTY,
                        propertyPath(boardUri) + "." + pd.getName()));
            }
        }
        return result;
    }

    private String propertyPath(String boardUri) {
        final String p = (basePath.isEmpty() ? "" : "." + basePath);
        return boardUri == null
                ? "vimboard.all" + p
                : "vimboard.custom" + p + "." + boardUri + "";
    }
}
