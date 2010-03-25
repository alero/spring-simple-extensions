package test.org.alex.springext.jdbc;

import org.alex.springext.jdbc.ExtendedSimpleJdbcTemplate;
import org.alex.springext.jdbc.InsertUpdater;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Robert
 * Date: 2010-mar-25
 * Time: 23:57:15
 * To change this template use File | Settings | File Templates.
 */


public class TestJdbcInsertUpdater {


    @Test
    @Ignore // i need the hipersonic db setup first
    public void testJdbc(){
        ExtendedSimpleJdbcTemplate extendedSimpleJdbcTemplate = new ExtendedSimpleJdbcTemplate(null);
        InsertUpdater insertUpdater = extendedSimpleJdbcTemplate.createUpdateTemplate("person");
        insertUpdater.field("date", new Date());
        extendedSimpleJdbcTemplate.update(insertUpdater);

    }


}
