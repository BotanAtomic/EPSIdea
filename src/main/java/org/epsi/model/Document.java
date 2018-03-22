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

    private String name, html;

    private User user;

    private int module;

    public Document(Database.SecureResult result) {
        this.id = result.getInt("id");
        this.path = result.getString("path");
        this.name = result.getString("name");

        this.module = result.getInt("module");

        this.user = User.users.get(result.getInt("user"));

        documents.put(this.id, this);
    }

    public Document(int id, String path, User user, int module) {
        this.id = id;
        this.path = path;

        this.module = module;

        this.user = user;

        documents.put(this.id, this);
    }

}
