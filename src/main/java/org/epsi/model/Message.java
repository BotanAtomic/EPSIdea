package org.epsi.model;

import lombok.Data;
import org.epsi.configuration.Database;

import java.util.Date;

@Data
public class Message {


    private int id;

    private String text;

    private User owner;

    private Document document;

    private Date date;

    private Module module;


    public Message(Database.SecureResult result) {
        this.id = result.getInt("id");
        this.text = result.getString("text");
        this.owner = User.users.get(result.getInt("user"));
        this.document = Document.documents.get(result.getInt("document"));
        this.module = Module.modules.get(result.getInt("module"));

        this.date = new Date(result.getLong("date"));
    }

}
