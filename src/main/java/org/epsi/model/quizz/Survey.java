package org.epsi.model.quizz;

import lombok.Data;
import org.epsi.configuration.Database;
import org.epsi.model.Module;
import org.epsi.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class Survey {

    public static Map<Integer, Survey> surveys = new ConcurrentHashMap<>();

    private int id;

    private String name, html;

    private User owner;

    private String question;

    private List<String> answers;

    private int valid;

    private int module;

    public Survey(Database.SecureResult result) {
        this.id = result.getInt("id");
        this.name = result.getString("name");
        this.question = result.getString("question");

        this.answers = Arrays.asList(result.getString("answers").split(";"));

        this.valid = result.getInt("valid_answer");

        this.module = result.getInt("module");

        this.owner = User.users.get(result.getInt("owner"));

        surveys.put(id, this);
    }



    @Override
    public int hashCode() {
        return this.id;
    }
}
