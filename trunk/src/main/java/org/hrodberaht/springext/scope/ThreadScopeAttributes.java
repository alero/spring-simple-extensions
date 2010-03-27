package org.hrodberaht.springext.scope;

import org.hrodberaht.directus.logging.SimpleLogger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Spring extensions 
 *
 * @author Robert Alexandersson
 * @version 1.0
 * @since 1.0
 */
public class ThreadScopeAttributes {
    protected final Map<String, Object> hBeans = new HashMap<String, Object>();
    protected final Map<String, Runnable> hRequestDestructionCallbacks = new LinkedHashMap<String, Runnable>();

    private static final SimpleLogger LOG = SimpleLogger.getInstance(ThreadScopeAttributes.class);

    /**
     * Gets bean <code>Map</code>.
     */
    protected final Map<String, Object> getBeanMap() {
        return hBeans;
    }

    /**
     * Register the given callback as to be executed after request completion.
     *
     * @param name     The name of the bean.
     * @param callback The callback of the bean to be executed for destruction.
     */
    protected final void registerRequestDestructionCallback(String name, Runnable callback) {
        // Assert.notNull(name, "Name must not be null");
        // Assert.notNull(callback, "Callback must not be null");

        hRequestDestructionCallbacks.put(name, callback);
    }

    /**
     * Clears beans and processes all bean destruction callbacks.
     */
    public final void clear() {
        processDestructionCallbacks();

        hBeans.clear();
    }

    /**
     * Processes all bean destruction callbacks.
     */
    private final void processDestructionCallbacks() {
        for (String name : hRequestDestructionCallbacks.keySet()) {
            Runnable callback = hRequestDestructionCallbacks.get(name);

            LOG.debug("Performing destruction callback for '" + name + "' bean" +
                    " on thread '" + Thread.currentThread().getName() + "'.");

            callback.run();
        }

        hRequestDestructionCallbacks.clear();
    }

}
