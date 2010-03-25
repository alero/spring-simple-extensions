package org.alex.springext.jdbc;

import org.alex.logging.SimpleLogger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;

/**
 * Spring extensions
 *
 * @author Robert Alexandersson
 * @version 1.0
 * @since 1.0
 */
public class ExtendedSimpleJdbcTemplate extends SimpleJdbcTemplate {

    private static final SimpleLogger logger = SimpleLogger.getInstance(ExtendedSimpleJdbcTemplate.class);

	public ExtendedSimpleJdbcTemplate(DataSource dataSource) {
		super(dataSource);
	}

	public int update(InsertUpdater update) {
        String data = update.getPreparedSql();
        Object[] args = update.getArgs();
        debugLogg(data, args);
		return super.update(data, args);
	}

    private void debugLogg(String data, Object[] args){
        if(logger.isDebugEnabled()){
            logger.debug(replaceSQLwithJava(data), args);
        }
    }

    private String replaceSQLwithJava(String data) {
        for(int i=0;data.indexOf("?") != -1;i++){
            data = data.replaceFirst("\\?", "{"+i+"}");
        }
        return data;
    }

    public InsertUpdater createUpdateTemplate(String tableName) {
		return new InsertUpdater(tableName);
	}


}
