package org.epsi.model;

import lombok.Data;
import org.epsi.configuration.Database;
import org.epsi.model.quiz.Survey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Skill {
    public static Map<Integer, Skill> skills = new ConcurrentHashMap<>();

    private int id;

    private String name;

    private Survey survey;

    public Skill(Database.SecureResult result) {
        this.id = result.getInt("id");
        this.name = result.getString("name");

        this.survey = Survey.surveys.get(result.getInt("survey"));

        skills.put(id, this);
    }

}
