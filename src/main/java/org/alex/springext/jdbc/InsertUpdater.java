package org.alex.springext.jdbc;

/**
 * Created by IntelliJ IDEA.
 * User: Robert
 * Date: 2010-mar-25
 * Time: 23:51:25
 * To change this template use File | Settings | File Templates.
 */
public interface InsertUpdater {
    InsertUpdater where(String name, Object value);
    InsertUpdater field(String name, Object value);
    
}
