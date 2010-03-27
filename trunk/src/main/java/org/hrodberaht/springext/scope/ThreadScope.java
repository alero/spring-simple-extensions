package org.alex.springext.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;

/**
 * Spring extensions
 *
 * @author Robert Alexandersson
 * @version 1.0
 * @since 1.0
 */
public class ThreadScope implements Scope {

    /**
     * Gets bean from org.alex.springext.scope.
     */
    public Object get(String name, ObjectFactory factory) {
        Object result = null;
        Map<String, Object> hBeans = ThreadScopeContextHolder.currentThreadScopeAttributes().getBeanMap();
        if (!hBeans.containsKey(name)) {
            result = factory.getObject();
            hBeans.put(name, result);
        } else {
            result = hBeans.get(name);
        }
        return result;
    }

    /**
     * Removes bean from org.alex.springext.scope.
     */
    public Object remove(String name) {
        Object result = null;
        Map<String, Object> hBeans = ThreadScopeContextHolder.currentThreadScopeAttributes().getBeanMap();
        if (hBeans.containsKey(name)) {
            result = hBeans.get(name);
            hBeans.remove(name);
        }
        return result;
    }

    public void registerDestructionCallback(String name, Runnable callback) {
        ThreadScopeContextHolder.currentThreadScopeAttributes().registerRequestDestructionCallback(name, callback);
    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    /**
     * Gets conversation id.  Not implemented.
     */
    public String getConversationId() {
        return null;
    }

}
