package com.example.demo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class LoginController {

    LoginService authService = new LoginService();
    LoginService.User user = LoginService.authenticate("john", "password123");
if (user != null) {
        if (user.role == LoginService.Role.MANAGER) {
            // Show all tee times and enable read/write/delete access
        } else if (user.role == LoginService.Role.MEMBER) {
            // Show only relevant tee times and enable reservation/cancellation
        }
    } else {
        // Authentication failed
    }

}
