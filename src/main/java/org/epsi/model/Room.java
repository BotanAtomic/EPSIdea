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

    private String name;

    private List<Module> modules = new CopyOnWriteArrayList<>();

    public Room(Database.SecureResult result) {
        this.id = result.getInt("id");
        this.name = result.getString("name");
    }

    public Room addModule(Module module) {
        this.modules.add(module);
        return this;
    }
}
