package com.github.vimboard.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Application environment based on pom.xml variables.
 */
class VimboardEnv {

    private static final String ENV_PROPERTIES_FILE = "com/github/vimboard/config/VimboardEnv.properties";

    private static final String basedir;
    private static final String version;

    static {
        InputStream is = VimboardEnv.class.getClassLoader()
                .getResourceAsStream(ENV_PROPERTIES_FILE);
        if (is != null) {
            Properties p = new Properties();
            try {
                p.load(is);
                basedir = p.getProperty("basedir");
                version = p.getProperty("version");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new RuntimeException("Property file '"
                    + ENV_PROPERTIES_FILE
                    + "' not found in the classpath.");
        }
    }

    /**
     * Returns application version.
     *
     * @return string, contains application version.
     */
    static String getBasedir() {
        return basedir;
    }

    /**
     * Returns application version.
     *
     * @return string, contains application version.
     */
    static String getVersion() {
        return version;
    }
}
