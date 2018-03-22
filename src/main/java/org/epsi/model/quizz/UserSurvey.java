package org.epsi.model.quizz;

import lombok.Data;
import org.epsi.model.User;

@Data
public class UserSurvey {

    private Survey survey;

    private User user;

    private int result;

    public UserSurvey(User user, Survey survey, int result) {
        this.survey = survey;
        this.user = user;
        this.result = result;
    }

}
