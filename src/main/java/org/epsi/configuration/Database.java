package org.epsi.configuration;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.epsi.model.*;
import org.epsi.model.quiz.Question;
import org.epsi.model.quiz.Survey;
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
        loadQuestions();

        loadDocuments();
        loadMessages();

        loadSkills();

        loadUserSurvey();
        loadUserModule();
        loadUserSkill();
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

    private void loadQuestions() {
        SecureResult secureResult = query("SELECT * from questions");

        int i = 0;
        while (secureResult.next()) {
            new Question(secureResult);
            i++;
        }

        System.err.println(i + " questions loaded");
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
            user.getSurveys().add(survey);
            i++;
        }

        System.err.println(i + " user survey loaded");
    }

    private void loadUserModule() {
        SecureResult secureResult = query("SELECT * from user_module");

        int i = 0;
        while (secureResult.next()) {
            User user = User.users.get(secureResult.getInt("user"));
            Module module = Module.modules.get(secureResult.getInt("module"));
            user.getModules().add(module);
            i++;
        }

        System.err.println(i + " user modules loaded");
    }

    private void loadUserSkill() {
        SecureResult secureResult = query("SELECT * from user_skills");

        int i = 0;
        while (secureResult.next()) {
            new UserSkill(secureResult);
            i++;
        }

        System.err.println(i + " user skill loaded");
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
