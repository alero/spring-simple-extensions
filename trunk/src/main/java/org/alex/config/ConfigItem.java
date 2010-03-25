package org.alex.config;

/**
 * Spring extensions
 *
 * @author Robert Alexandersson
 * @version 1.0
 * @since 1.0
 */
public class ConfigItem<T> {

    private String name;
    private T value;
    private Class clazz;

    public ConfigItem() {        
    }

    public ConfigItem(Class clazz, String s) {
        this.name = s;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


    public Class type() {
        return clazz;
    }
}
