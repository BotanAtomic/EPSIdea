package org.epsi.model;


import lombok.Data;
import org.epsi.configuration.Database;
import org.epsi.model.quiz.Survey;

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

    public void addUserSkill(UserSkill userSkill) {
        this.skills.add(userSkill);
    }


}
