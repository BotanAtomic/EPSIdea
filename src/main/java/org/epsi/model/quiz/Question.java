package org.epsi.model.quiz;

import lombok.Data;
import org.epsi.configuration.Database;

@Data
public class Question {

    private int id;

    private String text;

    private String[] answers;

    private int valid;

    public Question(Database.SecureResult result) {
        this.id = result.getInt("id");
        this.text = result.getString("text");

        this.answers = result.getString("answers").split(";");

        this.valid = result.getInt("valid_answer");

        Survey.surveys.get(result.getInt("survey")).addQuestion(this);
    }
}
