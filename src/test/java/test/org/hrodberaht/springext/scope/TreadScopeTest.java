package test.org.hrodberaht.springext.scope;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Robert
 * Date: 2010-mar-26
 * Time: 00:00:57
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath:org/hrodberaht/springext/scope/spring-config.xml"
                , "classpath:test/org/hrodberaht/springext/scope/test-spring-config.xml"}
)
public class TreadScopeTest {

    @Autowired
    private ThreadScopedBean bean;

    @Test
    public void getThreadedBean() {
        // This is a stupid test but it just test to see if the scope works as a fdefninition
        assertNotNull(bean);
    }

    // TODO: create a useful test for ThreadScoped beans

}
