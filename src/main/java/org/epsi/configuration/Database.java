package org.epsi.configuration;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@EnableAutoConfiguration
public class Database {

    private Connection connection = null;

    @Bean
    public Connection dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("EPSIdea");

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection == null)
                System.exit(-1500);
            else
                System.out.println("Database : connected !");
        }

        return connection;
    }

    private SecureResult query(String sql) {
        try {
            return new SecureResult(connection.createStatement().executeQuery(sql));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class SecureResult {

        private final ResultSet resultSet;

        SecureResult(ResultSet resultSet) {
            this.resultSet = resultSet;
        }

        public int getInt(String column) {
            try {
                return resultSet.getInt(column);
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }

        public long getLong(String column) {
            try {
                return resultSet.getLong(column);
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }

        public String getString(String column) {
            try {
                return resultSet.getString(column);
            } catch (SQLException e) {
                e.printStackTrace();
                return "";
            }
        }


    }


}
