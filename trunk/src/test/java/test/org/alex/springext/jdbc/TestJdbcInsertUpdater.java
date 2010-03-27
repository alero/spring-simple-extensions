package test.org.alex.springext.jdbc;

import org.alex.springext.jdbc.ExtendedSimpleJdbcTemplate;
import org.alex.springext.jdbc.InsertUpdater;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Robert
 * Date: 2010-mar-25
 * Time: 23:57:15
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath:org/alex/springext/tdd/datasource-spring-config.xml"}
)
public class TestJdbcInsertUpdater {


    @Autowired
    private DataSource dataSource;

    @Test
    public void testJdbcSelect(){
        ExtendedSimpleJdbcTemplate extendedSimpleJdbcTemplate = new ExtendedSimpleJdbcTemplate(dataSource);
        InsertUpdater insertUpdater = extendedSimpleJdbcTemplate.createUpdateTemplate("person");
        insertUpdater.field("id", 1);
        insertUpdater.field("name","John Doe");
        insertUpdater.field("email", "john@doe.com");
        insertUpdater.field("createdate", new Date());
        extendedSimpleJdbcTemplate.update(insertUpdater);

        int id = extendedSimpleJdbcTemplate.queryForInt("select id from person where id = ?", 1);
        assertEquals(1, id); 

    }

}
