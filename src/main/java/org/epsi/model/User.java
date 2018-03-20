package org.epsi.model;


import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class User {

    public static Map<Integer, User> users = new ConcurrentHashMap<>();

    private int id;
    private String email, password, username, surname, name, address;


}
