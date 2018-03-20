package org.epsi.model.quiz;

import lombok.Data;
import org.epsi.configuration.Database;
import org.epsi.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Survey {

    public static Map<Integer, Survey> surveys = new ConcurrentHashMap<>();

    private int id;

    private String name;

    private User owner;

    private int requiredPoint;

    public Survey(Database.SecureResult result) {
        this.id = result.getInt("id");
        this.name = result.getString("name");
        this.requiredPoint = result.getInt("required_point");

        this.owner = User.users.get(result.getInt("owner"));

        surveys.put(id, this);
    }



}
