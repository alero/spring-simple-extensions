package org.hrodberaht.springext.util;

import org.hrodberaht.directus.util.ioc.SimpleContainerInstanceCreator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple Java Utils
 *
 * @author Robert Alexandersson
 *         2010-apr-13 20:55:45
 * @version 1.0
 * @since 1.0
 */
public class SpringSimpleContainerInstanceCreator implements SimpleContainerInstanceCreator {

    private Map<Class, Class> serviceRegister = new HashMap<Class, Class>();
    private ApplicationContext context = null;

    public SpringSimpleContainerInstanceCreator(String[] locations, Class... services){
        for(Class service:services){
            serviceRegister.put(service, null);
        }
        context = new ClassPathXmlApplicationContext(locations);
    }

    @Override
    public <T> T getService(Class<T> service) {        
        return context.getBean(service);
    }

    @Override
    public boolean supportServiceCreation(Class service) {
        return serviceRegister.containsKey(service);
    }

    @Override
    public boolean supportForcedInstanceScope() {
        return false;
    }
}
