package org.hrodberaht.springext.scope;

import org.hrodberaht.directus.logging.SimpleLogger;

/**
 * Spring extensions
 *
 * @author Robert Alexandersson
 * @version 1.0
 * @since 1.0
 */
public class ThreadScopeContextHolder {
    private static final ThreadLocal<ThreadScopeAttributes> threadScopeAttributesHolder = new ThreadLocal<ThreadScopeAttributes>() {
        protected ThreadScopeAttributes initialValue() {
            return new ThreadScopeAttributes();
        }
    };

    private static final SimpleLogger LOG = SimpleLogger.getInstance(ThreadScopeContextHolder.class);

    /**
     * Gets <code>ThreadScopeAttributes</code>.
     */
    public static ThreadScopeAttributes getThreadScopeAttributes() {
        return threadScopeAttributesHolder.get();
    }

    /**
     * Gets current <code>ThreadScopeAttributes</code>.
     */
    public static ThreadScopeAttributes currentThreadScopeAttributes() throws IllegalStateException {

        ThreadScopeAttributes accessor = threadScopeAttributesHolder.get();
        LOG.debug("currentThreadScopeAttributes Thread: {0} Object: {1}", Thread.currentThread().toString(), accessor);        
        if (accessor == null) {
            throw new IllegalStateException("No thread scoped attributes.");
        }

        return accessor;
    }

}
