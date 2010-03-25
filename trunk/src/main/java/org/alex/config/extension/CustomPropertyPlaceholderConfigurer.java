package com.te.config.extention;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: ra0656
 * Date: 2009-maj-08
 * Time: 14:09:10
 * To change this template use File | Settings | File Templates.
 */
public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {


    protected String resolvePlaceholder(String placeholder, Properties props) {

        try {

            if (placeholder.startsWith("HOST.")) {
                String replace = placeholder.replaceFirst("HOST", InetAddress.getLocalHost().getHostName());
                return props.getProperty(replace);
            } else {
                return props.getProperty(placeholder);
            }
        } catch (UnknownHostException e) {
            return null;
        }
    }
}