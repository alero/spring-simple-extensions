package org.hrodberaht.springext.tdd;

import org.hrodberaht.directus.util.SocketCloser;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceFactory implements FactoryBean, InitializingBean {

    private String databaseName;
    private Resource schemaLocation;
    private Resource dataLocation;

    private DataSource dataSource;

    public DataSourceFactory() {
    }

    public DataSourceFactory(String testDatabaseName, Resource schemaLocation, Resource testDataLocation) {
        setTestDatabaseName(testDatabaseName);
        setSchemaLocation(schemaLocation);
        setTestDataLocation(testDataLocation);
    }

    public void setTestDatabaseName(String testDatabaseName) {
        this.databaseName = testDatabaseName;
    }


    public void setSchemaLocation(Resource schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public void setTestDataLocation(Resource testDataLocation) {
        this.dataLocation = testDataLocation;
    }

    public void afterPropertiesSet() {
        if (dataLocation != null) {
            initDataSource();
        }
    }

    public Object getObject() throws Exception {
        return getDataSource();
    }

    public Class getObjectType() {
        return DataSource.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public DataSource getDataSource() {
        if (dataSource == null) {
            initDataSource();
        }
        return dataSource;
    }

    private void initDataSource() {
        // create a data source
        this.dataSource = createDataSource();
        populateDataSource();

    }

    private DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // load driver
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        // create it as an in-memmory version
        dataSource.setUrl("jdbc:hsqldb:mem:" + databaseName);
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    private void populateDataSource() {
        TestDatabasePopulator populator = new TestDatabasePopulator(dataSource);
        populator.populate();
    }

    private class TestDatabasePopulator {

        private DataSource dataSource;

        public TestDatabasePopulator(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        public void populate() {
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
                createDatabaseSchema(connection);
                insertTestData(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                SocketCloser.close(connection);
            }
        }

        private void createDatabaseSchema(Connection connection) {
            try {
                if (schemaLocation != null) {
                    String sql = parseSqlIn(schemaLocation);
                    executeSql(connection, sql);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        private void insertTestData(Connection connection) {
            try {
                if (dataLocation != null) {
                    String sql = parseSqlIn(dataLocation);
                    executeSql(connection, sql);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        private String parseSqlIn(Resource resource) throws IOException {
            InputStream is = null;
            try {
                is = resource.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                StringWriter sw = new StringWriter();
                BufferedWriter writer = new BufferedWriter(sw);

                for (int c = reader.read(); c != -1; c = reader.read()) {
                    writer.write(c);
                }
                writer.flush();
                return sw.toString();

            } finally {
                SocketCloser.close(is);
            }
        }

        private void executeSql(Connection connection, String sql) throws SQLException {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            SocketCloser.close(statement);
        }

    }
}