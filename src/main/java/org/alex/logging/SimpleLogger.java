package org.alex.logging;

/**
 * Spring extensions
 *
 * @author Robert Alexandersson
 * @version 1.0
 * @since 1.0
 */
public class SimpleLogger {
    public static SimpleLogger getInstance(Class clazz) {
        return new SimpleLogger();
    }

    public void debug(String message) {

    }

    public void debug(String message, Object... args) {

    }

    public boolean isDebugEnabled() {
        return false;
    }

    public void info(String message) {

    }

    public void info(String s, Object... args) {
    }

    public void error(Exception e) {
        
    }
}
