package org.hrodberaht.springext.jdbc;

/**
 * Spring extensions
 *
 * @author Robert Alexandersson
 * @version 1.0
 * @since 1.0
 */
public interface InsertUpdater {
    InsertUpdater where(String name, Object value);
    InsertUpdater field(String name, Object value);
    
}
