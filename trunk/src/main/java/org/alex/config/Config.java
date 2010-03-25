package org.alex.config;

import java.text.ParseException;

/**
 * Spring extensions
 *
 * @author Robert Alexandersson
 * @version 1.0
 * @since 1.0
 */
public interface Config {


    boolean requiresValidation();
    boolean validate();
    void initiate() throws IllegalAccessException;
    void loadProperties() throws ParseException;
}
