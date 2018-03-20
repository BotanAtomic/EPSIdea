package org.epsi.configuration;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Connection;
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


}
