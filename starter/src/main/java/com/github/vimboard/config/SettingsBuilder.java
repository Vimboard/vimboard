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

/**
 * Построитель объекта настроек. Поля этого объекта инициализируется только
 * указанными значениями из объекта свойств. При отсутствии указанных свойсв
 * используются соответствующие значения из шаблонного объекта настроек (URI
 * доски должен быть указан).
 * Если построитель используется для создания шаблонного объекта настроек, то
 * URI доски указывается равным {@code null} и при отсутствии нужных свойсв
 * используются соответствующие значения по умолчанию.
 *
 * @param <P> тип объекта свойств.
 * @param <S> тип объекта настроек.
 */
public class SettingsBuilder<P, S> {

    private static final String INVALID_BEAN = "Invalid '%s' bean";
    private static final String INVALID_BEAN_METHOD = "Can't find method for '%s' bean poperty";
    private static final String INVALID_BEAN_PROPERTY = "Invalid '%s' bean poperty";
    private static final String INVALID_PROPERTY = "Invalid '%s' property value";

    /**
     * Конвертер для значения из объекта свойств. Нужен, если тип поля объекта
     * свойств отличается от типа поля объекта настроек.
     */
    public interface SettingsBuilderConverter {

        /**
         * Конвертирует значение поля объекта свойств в значение поля
         * объекта настроек.
         *
         * @param boardUri URI достки.
         * @param value значение поля объекта свойств.
         * @return значение поля объекта настроек.
         */
        Object convert(String boardUri, Object value);
    }

    /** URI достки. */
    private final String boardUri;

    /** Путь до корня, в котором лежит объект свойств. */
    private final String basePath;

    /** Ассоциативный массив объекта свойств */
    private final Map<String, Object> propertiesMap;

    /** Ассоциативный массив шаблонного объекта настроек. */
    private final Map<String, Object> templateMap;

    /** Ассоциативный массив объекта настроек. */
    private final Map<String, Object> settingsMap;

    /** Целевой объект настроек. */
    private final S settings;

    /**
     * Создаёт построитель настроек.
     *
     * @param boardUri URI достки.
     * @param basePath путь до корня, в котором лежит объект свойств объекта
     *                 настроек, относительно объекта свойств доски, разделённый
     *                 точками. Если строится объек настроек доски, то путь
     *                 равен "".
     * @param properties объект свойств.
     * @param allSettings шаблонный объект настроек.
     * @param resultSettings целевой объект настроек.
     */
    public SettingsBuilder(String boardUri, String basePath,
            P properties, S allSettings, S resultSettings) {
        this.boardUri = boardUri;
        this.basePath = basePath;
        propertiesMap = objectToMap(properties);
        templateMap = objectToMap(allSettings);
        this.settingsMap = new HashMap<>();
        settings = resultSettings;
    }

    /**
     * Выполняет построение объекта настроек. В текущей реализации это
     * заполнение полей {@link #settings} значениями из {@link #settingsMap}.
     *
     * @return объект настроек.
     */
    public S build() {
        final BeanInfo info;
        try {
            info = Introspector.getBeanInfo(settings.getClass());
        } catch (IntrospectionException ex) {
            throw new ValidationException(String.format(INVALID_BEAN,
                    propertyPath(basePath, boardUri)));
        }
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            if (pd.getName().equals("class")) {
                continue;
            }
            Method setter = pd.getWriteMethod();
            if (setter == null) {
                throw new ValidationException(String.format(INVALID_BEAN_METHOD,
                        "setter: " + propertyPath(basePath, boardUri) + "." + pd.getName()));
            }
            try {
                setter.invoke(settings, settingsMap.get(pd.getName()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ValidationException(String.format(INVALID_BEAN_PROPERTY,
                        propertyPath(basePath, boardUri) + "." + pd.getName()));
            }
        }
        return settings;
    }

    /**
     * Инициализирует значение поля объекта настроек.
     *
     * @param fieldName имя поля.
     * @param defaultValue значение по умолчанию для поля.
     * @return значение поля объекта настроек.
     */
    public Object put(String fieldName, Object defaultValue) {
        if (propertiesMap == null || propertiesMap.get(fieldName) == null) {
            settingsMap.put(fieldName, (boardUri == null
                    ? defaultValue
                    : templateMap.get(fieldName)));
        } else {
            settingsMap.put(fieldName, propertiesMap.get(fieldName));
        }
        return settingsMap.get(fieldName);
    }

    /**
     * Инициализирует значение поля объекта настроек.
     *
     * @param fieldName имя поля.
     * @param defaultValue значение по умолчанию для поля.
     * @param converter конвертер для значения из объекта свойств.
     * @return значение поля объекта настроек.
     */
    public Object put(String fieldName, Object defaultValue,
            SettingsBuilderConverter converter) {
        if (propertiesMap == null || propertiesMap.get(fieldName) == null) {
            settingsMap.put(fieldName, (boardUri == null
                    ? defaultValue
                    : templateMap.get(fieldName)));
        } else {
            settingsMap.put(fieldName, converter.convert(
                    this.boardUri, propertiesMap.get(fieldName)));
        }
        return settingsMap.get(fieldName);
    }

    /**
     * Конвертирует объект в ассоциативный массив.
     *
     * @param object заданный объект.
     * @return ассоциативный массив.
     */
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
                    propertyPath(basePath, boardUri)));
        }
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            if (pd.getName().equals("class")) {
                continue;
            }
            Method getter = pd.getReadMethod();
            if (getter == null) {
                throw new ValidationException(String.format(INVALID_BEAN_METHOD,
                        "getter: " + propertyPath(basePath, boardUri) + "." + pd.getName()));
            }
            try {
                result.put(pd.getName(), getter.invoke(object));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ValidationException(String.format(INVALID_BEAN_PROPERTY,
                        propertyPath(basePath, boardUri) + "." + pd.getName()));
            }
        }
        return result;
    }

    /**
     * Возвращает полный путь до корня объекта свойств.
     *
     * @param basePath путь до корня, в котором лежит объект свойств.
     * @param boardUri URI доски.
     * @return полное имя свойства.
     */
    private static String propertyPath(String basePath, String boardUri) {
        final String p = (basePath.isEmpty() ? "" : "." + basePath);
        return boardUri == null
                ? "vimboard.all" + p
                : "vimboard.custom" + p + "." + boardUri + "";
    }

    /**
     * Бросает исключение невалидного значения поля объекта свойств.
     *
     * @param basePath путь до корня, в котором лежит объект свойств.
     * @param boardUri URI доски.
     * @param fieldName имя поля.
     */
    public static void throwInvalidProperty(String basePath, String boardUri,
            String fieldName) {
        throw new ValidationException(String.format(INVALID_PROPERTY,
                propertyPath(basePath, boardUri) + "." + fieldName));
    }
}
