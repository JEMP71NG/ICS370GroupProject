package com.example.demo;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class LoginService {

    public enum Role {
        MEMBER,
        MANAGER
    }

    public static User authenticate(String username, String password) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("users.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            if(jsonObject.containsKey("username") && jsonObject.containsKey("password")) {
            String readUsername = (String) jsonObject.get("username");
            String readPassword = (String) jsonObject.get("password");
            if(readPassword.equals(username) && readPassword.equals(password)) {
                String roleString = (String) jsonObject.get("role");
                Role role = Role.valueOf(roleString);
                return new User(username, role);
        }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null; // Authentication failed
    }

    public static class User {
        public String username;
        public String password;
        public Role role;

        public User(String username, Role role) {
            this.username = username;
            this.role = role;
        }
    }
}
