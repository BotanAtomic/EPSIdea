package org.epsi.model;


import lombok.Data;
import org.epsi.configuration.Database;
import org.epsi.model.quizz.Survey;
import org.epsi.model.quizz.UserSurvey;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class User {

    public static Map<Integer, User> users = new ConcurrentHashMap<>();

    private int id;
    private String email, password, username, surname, name, address;

    private List<Module> modules = new CopyOnWriteArrayList<>();

    private List<UserSkill> skills = new CopyOnWriteArrayList<>();

    private List<Survey> surveys = new CopyOnWriteArrayList<>();

    private List<UserSurvey> userSurveys = new CopyOnWriteArrayList<>();

    public User(Database.SecureResult result) {
        this.id = result.getInt("id");
        this.email = result.getString("email");
        this.password = result.getString("password");
        this.username = result.getString("username");
        this.surname = result.getString("surname");
        this.name = result.getString("name");
        this.address = result.getString("address");

        users.put(id, this);
    }

    public User(int id, String email, String password, String username, String surname, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.surname = surname;
        this.name = name;

        users.put(id, this);
    }

    public void addUserSkill(UserSkill userSkill) {
        this.skills.add(userSkill);
    }


    @Override
    public int hashCode() {
        return this.id;
    }

}
