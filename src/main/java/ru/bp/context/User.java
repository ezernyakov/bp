package ru.bp.context;

import java.util.HashMap;
import java.util.Map;

public enum User {

    ADMINISTRATOR("Администратор", "admin", "password"),
    MANAGER("Менеджер", "manager", "qwerty"),
    SERVICE_ENGINEER("Инженер", "service", "service"),
    FINANCE_MANAGER("Специалист отдела финансов", "finance", "fin123"),
    ;

    private static Map<String, User> roles = new HashMap<>();

    private String role;
    private String login;
    private String password;

    static {
        for (User user : User.values()) {
            roles.put(user.getLogin(), user);
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    User(String role, String login, String password) {
        this.role = role;
        this.login = login;
        this.password = password;
    }

    public static User getUser(String value) {
        return roles.get(value);
    }
}

