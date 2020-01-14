package com.github.vimboard.config;

import javax.validation.ValidationException;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class SettingsBean {

    private static final String INVALID_BEAN = "Invalid '%s' bean";
    private static final String INVALID_BEAN_METHOD = "Can't find method for '%s' bean poperty";
    private static final String INVALID_BEAN_PROPERTY = "Invalid '%s' bean poperty";
    private static final String INVALID_PROPERTY = "Invalid '%s' property value";

    private interface SettingsReaderConverter {

        Object convert(SettingsReader reader, Object value);
    }

    private static class SettingsReader {

        final String boardUri;
        final Map<String, Object> propertiesMap;
        final Map<String, Object> allSettingsMap;
        final Map<String, Object> settingsMap;

        public SettingsReader(String boardUri,
                VimboardBoardProperties properties,
                VimboardBoardSettings allSettings) {
            this.boardUri = boardUri;
            propertiesMap = objectToMap(properties);
            allSettingsMap = objectToMap(allSettings);
            this.settingsMap = new HashMap<>();
        }

        public VimboardBoardSettings build() {
            final VimboardBoardSettings result = new VimboardBoardSettings();
            final BeanInfo info;
            try {
                info = Introspector.getBeanInfo(result.getClass());
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
                    setter.invoke(result, settingsMap.get(pd.getName()));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new ValidationException(String.format(INVALID_BEAN_PROPERTY,
                            propertyPath(boardUri) + "." + pd.getName()));
                }
            }
            return result;
        }

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
                SettingsReaderConverter converter) {
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
    }

    private final VimboardSettings vimboardSettings;

    public SettingsBean(VimboardProperties vimboardProperties) {
        vimboardSettings = new VimboardSettings();

        final String www = vimboardProperties.getWww();
        vimboardSettings.setWww(www == null || www.isEmpty()
                ? "/var/www/vimboard/public/" : www);

        vimboardSettings.setAll(buildBoardSettings(
                vimboardProperties, null));

        Map<String, VimboardBoardSettings> customSettings = new HashMap<>();
        if (vimboardProperties.getCustom() != null) {
            for (String boardUri : vimboardProperties.getCustom().keySet()) {
                customSettings.put(boardUri, buildBoardSettings(
                        vimboardProperties, boardUri));
            }
        }
        vimboardSettings.setCustom(customSettings);
    }

    /**
     * Returns the vimboard settings.
     *
     * @return vimboard settings.
     */
    public VimboardSettings get() {
        return vimboardSettings;
    }

    /**
     * Returns board settings for all boards.
     *
     * @return board settings.
     */
    public VimboardBoardSettings getAll() {
        return vimboardSettings.getAll();
    }

    /**
     * Returns board settings by the board uri.
     *
     * @param boardUri the board uri.
     * @return board settings.
     */
    public VimboardBoardSettings getCustom(String boardUri) {
        final VimboardBoardSettings result =
                vimboardSettings.getCustom().get(boardUri);
        return result == null ? vimboardSettings.getAll() : result;
    }

    private VimboardBoardSettings buildBoardSettings(
            VimboardProperties vimboardProperties, String boardUri) {
        final VimboardBoardProperties p = (boardUri == null
                ? vimboardProperties.getAll()
                : vimboardProperties.getCustom().get(boardUri));
        final VimboardBoardSettings a = vimboardSettings.getAll();

        SettingsReader sr = new SettingsReader(boardUri, p, a);

        sr.put("boardAbbreviation", "/{uri}/");
        sr.put("boardPath", "{uri}/");
        sr.put("boardlistWrapBracket", false);
        sr.put("boards", null, this::convertBoards);
        sr.put("countryFlagsCondensed", true);
        sr.put("countryFlagsCondensedCss", "static/flags/flags.css");
        sr.put("fileIndex", "index.html");
        final String fileScript = (String) sr.put("fileScript", "main.js");
        sr.put("fontAwesome", true);
        sr.put("fontAwesomeCss", "stylesheets/font-awesome/css/font-awesome.min.css");
        sr.put("metaKeywords", null);
        final String root = (String) sr.put("root", "/");
        final Map<String, String> stylesheets;
        {
            Map<String, String> defaultValue = new HashMap<>(2);
            defaultValue.put("Yotsuba B", "");
            defaultValue.put("Yotsuba", "yotsuba.css");
            //noinspection unchecked
            stylesheets = (Map<String, String>) sr.put("stylesheets", defaultValue);
        }

        // Settings with dependencies

        sr.put("defaultStylesheet",
                new String[] { "Yotsuba B", stylesheets.get("Yotsuba B") }, (reader, value) -> {
                    return convertDefaultStylesheet(reader, value, stylesheets);
                });
        final String uriStylesheets = (String) sr.put("uriStylesheets", root + "stylesheets/");
        sr.put("urlJavascript", root + fileScript);
        sr.put("urlStylesheet", uriStylesheets + "style.css");

        return sr.build();
    }

    @SuppressWarnings("rawtypes")
    private Object[] convertBoards(SettingsReader sr, Object value) {
        final String boardUri = sr.boardUri;
        final Map boards = (Map) value;
        if (boards == null) {
            return null;
        }
        final int len = boards.keySet().size();
        Object[] result = new Object[len];
        for (int i = 0; i < len; i++) {
            Object obj = boards.get(Integer.toString(i));
            if (obj == null) {
                throwInvalid(boardUri);
            } else if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.keySet().size() != 1) {
                    throwInvalid(boardUri);
                }
                for (Object key : map.keySet()) {
                    if (!(key instanceof String)) {
                        throwInvalid(boardUri);
                    }
                    Object val = map.get(key);
                    if (val instanceof Map) {
                        result[i] = new AbstractMap.SimpleEntry<>(key, convertBoards(sr, val));
                    } else if (val instanceof String) {
                        result[i] = new AbstractMap.SimpleEntry<>(key, val);
                    } else {
                        throwInvalid(boardUri);
                    }
                }
            } else if (obj instanceof String) {
                result[i] = obj;
            } else {
                throwInvalid(boardUri);
            }
        }
        return result;
    }

    private Object convertDefaultStylesheet(SettingsReader sr, Object value,
            Map<String, String> stylesheets) {
        final String defaultStylesheet = (String) value;
        return new String[] { defaultStylesheet, stylesheets.get(defaultStylesheet) };
    }

    private void throwInvalid(String boardUri) {
        throw new ValidationException(String.format(INVALID_PROPERTY,
                propertyPath(boardUri) + ".boards"));
    }

    private static String propertyPath(String boardUri) {
        return boardUri == null
                ? "vimboard.all"
                : "vimboard.custom." + boardUri + "";
    }
}
