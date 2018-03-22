package org.epsi.configuration;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.epsi.model.*;
import org.epsi.model.quizz.Survey;
import org.epsi.model.quizz.UserSurvey;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Service
@EnableAutoConfiguration
public class Database {

    public static Database database;

    public Connection connection = null;

    @Bean
    public Connection dataSource() {
        database = this;

        Properties properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("app.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(properties.getProperty("database.user"));
        dataSource.setPassword(properties.getProperty("database.password"));
        dataSource.setServerName(properties.getProperty("database.host"));
        dataSource.setDatabaseName(properties.getProperty("database.name"));

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection == null)
                System.exit(-1500);
            else {
                System.out.println("Database : connected !");
                loadAll();
            }
        }

        return connection;
    }

    private void loadAll() {
        loadRooms();
        loadModules();

        loadUsers();

        loadSurveys();

        loadDocuments();
        loadMessages();

        loadSkills();

        loadUserSurvey();
    }

    private void loadRooms() {
        SecureResult secureResult = query("SELECT * from rooms");

        while (secureResult.next()) {
            new Room(secureResult);
        }

        System.err.println(Room.rooms.size() + " rooms loaded");
    }

    private void loadModules() {
        SecureResult secureResult = query("SELECT * from modules");

        while (secureResult.next()) {
            new Module(secureResult);
        }

        System.err.println(Module.modules.size() + " modules loaded");
    }

    private void loadUsers() {
        SecureResult secureResult = query("SELECT * from users");

        while (secureResult.next()) {
            new User(secureResult);
        }

        System.err.println(User.users.size() + " users loaded");
    }

    private void loadSurveys() {
        SecureResult secureResult = query("SELECT * from surveys");

        while (secureResult.next()) {
            new Survey(secureResult);
        }

        System.err.println(Survey.surveys.size() + " surveys loaded");
    }


    private void loadDocuments() {
        SecureResult secureResult = query("SELECT * from documents");

        while (secureResult.next()) {
            new Document(secureResult);
        }

        System.err.println(Document.documents.size() + " documents loaded");
    }

    private void loadMessages() {
        SecureResult secureResult = query("SELECT * from messages");

        while (secureResult.next()) {
            new Message(secureResult);
        }

        System.err.println(Message.messages.size() + " messages loaded");
    }

    private void loadSkills() {
        SecureResult secureResult = query("SELECT * from skills");

        while (secureResult.next()) {
            new Skill(secureResult);
        }

        System.err.println(Skill.skills.size() + " skills loaded");
    }

    private void loadUserSurvey() {
        SecureResult secureResult = query("SELECT * from user_survey");

        int i = 0;
        while (secureResult.next()) {
            User user = User.users.get(secureResult.getInt("user"));
            Survey survey = Survey.surveys.get(secureResult.getInt("survey"));
            user.getUserSurveys().add(new UserSurvey(user, survey, secureResult.getInt("result")));
            i++;
        }

        System.err.println(i + " user surveys loaded");
    }


    public SecureResult query(String sql) {
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


        public boolean next() {
            try {
                return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }


}
