package com.github.vimboard.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Application version based on pom.xml project version.
 */
public class VimboardVersion {

    private static final String VERSION_PROPERTIES_FILE = "com/github/vimboard/config/VimboardVersion.properties";

    private static final String release;

    static {
        InputStream is = VimboardVersion.class.getClassLoader()
                .getResourceAsStream(VERSION_PROPERTIES_FILE);
        if (is != null) {
            Properties p = new Properties();
            try {
                p.load(is);
                release = p.getProperty("release");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new RuntimeException("Property file '"
                    + VERSION_PROPERTIES_FILE
                    + "' not found in the classpath.");
        }
    }

    /**
     * Returns application verstion number.
     *
     * @return string, contains application version number.
     */
    public static String get() {
        return release;
    }
}
