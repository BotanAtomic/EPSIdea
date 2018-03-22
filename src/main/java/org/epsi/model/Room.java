package org.epsi.model;

import lombok.Data;
import org.epsi.configuration.Database;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class Room {

    public static Map<Integer, Room> rooms = new ConcurrentHashMap<>();

    private int id;

    private String name, image, description;

    private List<Module> modules = new CopyOnWriteArrayList<>();


    public Room(Database.SecureResult result) {
        this.id = result.getInt("id");
        this.name = result.getString("name");
        this.description = result.getString("description");
        this.image = result.getString("image");

        rooms.put(id, this);
    }

    public Room addModule(Module module) {
        this.modules.add(module);
        return this;
    }

    public int hashCode() {
        return id;
    }
}
