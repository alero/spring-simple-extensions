package org.alex.config;

/**
 * Created by IntelliJ IDEA.
 * User: Robert
 * Date: 2010-mar-25
 * Time: 23:27:10
 * To change this template use File | Settings | File Templates.
 */
public class ConfigException extends RuntimeException{
    public ConfigException() {
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }
}
