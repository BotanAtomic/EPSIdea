package org.epsi.model;

import lombok.Data;
import org.epsi.configuration.Database;

@Data
public class UserSkill {

    private boolean validate;

    private int point;

    private Skill skill;

    public UserSkill(Database.SecureResult result) {
        this.validate = result.getInt("validate") == 1;
        this.point = result.getInt("point");

        this.skill = Skill.skills.get(result.getInt("skill"));

        User.users.get(result.getInt("user")).addUserSkill(this);
    }



}
