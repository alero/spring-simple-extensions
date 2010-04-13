package test.org.hrodberaht.springext.util;

import org.hrodberaht.directus.exception.MessageRuntimeException;
import org.hrodberaht.directus.util.ioc.JavaContainerRegister;
import org.hrodberaht.directus.util.ioc.SimpleContainer;
import org.hrodberaht.springext.util.SpringSimpleContainerInstanceCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.org.hrodberaht.springext.samplebean.SpringWiredService;

import static org.junit.Assert.assertEquals;

/**
 * Simple Java Utils
 *
 * @author Robert Alexandersson
 *         2010-apr-13 20:57:16
 * @version 1.0
 * @since 1.0
 */
public class TestSpringSimpleContainerInstanceCreator {
    @Before
    public void init(){
        String[] location = { "classpath:test/org/hrodberaht/springext/samplebean/test-spring-config.xml" };
        SpringSimpleContainerInstanceCreator instanceCreator =
                new SpringSimpleContainerInstanceCreator(location, SpringWiredService.class);
        JavaContainerRegister.registerInstanceCreator(instanceCreator);
    }

    @After
    public void destroy(){
        JavaContainerRegister.registerInstanceCreator(null);        
    }

    @Test
    public void testRegister(){
        SpringWiredService wiredService = SimpleContainer.get(SpringWiredService.class);
        wiredService.doSpringThing();
        assertEquals("Test of Service", wiredService.getSpringThing());
        assertEquals("SubService Information", wiredService.getSubServiceSpringThing());
    }

    @Test(expected = MessageRuntimeException.class)
    public void testBadNewRegister(){
        SpringWiredService wiredService = SimpleContainer.getNew(SpringWiredService.class);
        wiredService.doSpringThing();
        assertEquals("Test of Service", wiredService.getSpringThing());
    }

    @Test(expected = MessageRuntimeException.class)
    public void testBadSingletonRegister(){
        SpringWiredService wiredService = SimpleContainer.getSingleton(SpringWiredService.class);
        wiredService.doSpringThing();
        assertEquals("Test of Service", wiredService.getSpringThing());
    }


}
