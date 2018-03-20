package org.epsi.model;

import lombok.Data;
import org.epsi.configuration.Database;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Document {

    public static Map<Integer, Document> documents = new ConcurrentHashMap<>();

    private int id;

    private String path;

    private User user;

    public Document(Database.SecureResult result) {
        this.id = result.getInt("id");
        this.path = result.getString("path");

        this.user = User.users.get(result.getInt("user"));

        documents.put(this.id, this);
    }

}
