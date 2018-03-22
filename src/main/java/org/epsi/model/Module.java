package org.epsi.model;

import lombok.Data;
import org.epsi.configuration.Database;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Module {
    public static Map<Integer, Module> modules = new ConcurrentHashMap<>();

    private int id;

    private String name;

    private Room room;

    public int documents, surveys;


    public Module(Database.SecureResult result) {
        this.id = result.getInt("id");
        this.name = result.getString("name");

        this.room = Room.rooms.get(result.getInt("room")).addModule(this);

        modules.put(id, this);
    }

    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
