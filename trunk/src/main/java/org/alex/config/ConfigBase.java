package org.alex.config;

import org.alex.logging.SimpleLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Ediel Edifact Management Software
 * Date: 2009-jan-07 15:06:40
 *
 * @author Robert Alexandersson
 * @version 2.0
 * @since 2.0
 */
public abstract class ConfigBase implements Config  {

    protected SimpleLogger LOGGER = null;
    protected String propertyPath = null;
    protected String customPropertyPath = null;

    protected Map<ConfigItem, ConfigItem> configurations = new HashMap();

    private static final long RELOAD = 15000;
    private static long timestamp;

    private Properties origproperties = null;
    private Properties cproperties = null;
    private Properties properties = null;

    protected void configure(Class clazz, String property) {
        configure(clazz, property, null);
    }

    protected void configure(Class clazz, String property, String customProperty) {
        LOGGER = SimpleLogger.getInstance(clazz);
        propertyPath = property;
        customPropertyPath = customProperty;
        try {
            Config config = (Config) clazz.newInstance();
            if(config.requiresValidation()){
                config.validate();                
            }
            MasterConfig.registerConfig(config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadProperties() throws ParseException {
        long elapsedTime = System.currentTimeMillis() - timestamp;
        if (origproperties == null) {
            reloadProperties();
            logProperties();
        } else if ((timestamp == 0 || (elapsedTime > RELOAD))) {
            reloadProperties();
            timestamp = System.currentTimeMillis();
        }
    }

    private void reloadProperties() throws ParseException {
        origproperties = new Properties();
        cproperties = new Properties();
        loadProperties(origproperties, propertyPath);
        loadProperties(cproperties, customPropertyPath);
        mergeProperties();
        setValues();

    }

    private void setValues() throws ParseException {
        for (ConfigItem conf : configurations.keySet()) {
            String value = System.getProperty(conf.getName());
            if(StringUtil.isBlank(value)){
                value = properties.getProperty(conf.getName());                
            }
            if (conf.type().isAssignableFrom(Boolean.class)) {
                conf.setValue(Boolean.parseBoolean(value));
            } else if (conf.type().isAssignableFrom(Integer.class)) {
                conf.setValue(Integer.parseInt(value));
            } else if (conf.type().isAssignableFrom(Long.class)) {
                conf.setValue(Long.parseLong(value));
            } else if (conf.type().isAssignableFrom(String[].class)) {
                conf.setValue(value.split(","));
            } else if (conf.type().isAssignableFrom(Date.class)) {
                conf.setValue(DateUtil.parseSimpleDate(value));
            } else {
                conf.setValue(value);
            }
            // conf.setValue(value);
        }
    }

    private void loadProperties(Properties props, String stream) {
        InputStream data = null;
        try {

            if (stream == null) {
                LOGGER.info("Property file not defined");
                return;
            }

            if (stream.startsWith("classpath:")) {
                String path = stream.replaceFirst("classpath:", "");
                data = ConfigBase.class.getResourceAsStream(path);
            } else if (stream.startsWith("file:")) {
                File file = new File(stream.replaceFirst("file:", ""));
                data = new FileInputStream(file);
            }
            if (data != null) {
                props.load(data);
            } else {
                LOGGER.info("Property file not found {0}", stream);
            }
        } catch (IOException e) {
            LOGGER.error(e);             
        } finally {
            close(data);
        }
    }


    private void mergeProperties() {
        properties = origproperties;
        if (cproperties != null) {
            Enumeration data = cproperties.keys();
            while (data.hasMoreElements()) {
                String key = (String) data.nextElement();
                String value = cproperties.getProperty(key);                
                properties.put(key, value);
            }
        }
    }

    private void logProperties() {
        LOGGER.info("Loading properties...");
        Enumeration keys = properties.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();            
            String value = System.getProperty(key);
            if(StringUtil.isBlank(value)){
                value = (String) properties.get(key);
            }
            LOGGER.info("Property key: {0}, value: {1}", key, value);
        }
        LOGGER.info("Properties loaded");
    }

    private static void close(InputStream data) {
        try {
            if (data != null) {
                data.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class StringUtil {
        public static boolean isBlank(String value) {
            return value == null || "".equals(value);  //To change body of created methods use File | Settings | File Templates.
        }
    }

    private static class DateUtil {
        public static Date parseSimpleDate(String value) throws ParseException {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(value);
        }
    }

    public void initiate() throws IllegalAccessException {
        // TODO: Create crazy reflection to autoload the properties

        configurations.clear();

        // TODO: redo this using reflection...

        checkForFields(this.getClass());
        Class[] interfaceses = this.getClass().getDeclaredClasses();
        checkInterfaces(interfaceses);
    }

    private void checkInterfaces(Class[] interfaceses) throws IllegalAccessException {
        for(Class ainterface:interfaceses){
            if(ainterface.isInterface()){
                checkForFields(ainterface);
                // Recursive
                Class[] innerinterfaceses = ainterface.getInterfaces();
                checkInterfaces(innerinterfaceses);
            }
        }
    }

    private void checkForFields(Class aClass) throws IllegalAccessException {
        Field[] fields = aClass.getDeclaredFields();
        for(Field field:fields){
            if(field.getType().isAssignableFrom(ConfigItem.class)){
                ConfigItem obj = (ConfigItem) field.get(new ConfigItem());
                configurations.put(obj, obj);
            }
        }
    }


    public boolean requiresValidation() {
        return false;
    }


    public boolean validate() {
        return false;
    }
}
