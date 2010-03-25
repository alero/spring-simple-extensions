package test.org.alex.config;

import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Robert
 * Date: 2010-mar-25
 * Time: 23:07:16
 * To change this template use File | Settings | File Templates.
 */
public class TestApplicationConfig {

    @BeforeClass
    public static void init() throws ParseException {
        AnyApplicationConfig.initConfig();
    }


    @Test
    public void testConfig() throws ParseException {
        assertEquals(new Boolean(true), AnyApplicationConfig.ApplicationState.A_BOOLEAN.getValue());
        assertEquals("Hello", AnyApplicationConfig.ApplicationState.A_STRING.getValue());
        assertEquals(parseDate("2010-05-05"), AnyApplicationConfig.ApplicationState.A_DATE.getValue());
        assertEquals(new Integer(5), AnyApplicationConfig.ApplicationState.A_INTEGER.getValue());
        assertEquals(new Long(5050505055505000L), AnyApplicationConfig.ApplicationState.A_LONG.getValue());

    }

    private Date parseDate(String s) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(s);
    }

}
