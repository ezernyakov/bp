package ru.bp.context;

import java.util.HashMap;
import java.util.Map;

public enum User {

    ADMINISTRATOR("Администратор", "password"),
    MANAGER("Менеджер", "qwerty"),
    SERVICE_ENGINEER("Инженер", "service"),
    FINANCE_MANAGER("Специалист отдела финансов", "fin123"),
    ;

    private static Map<String, User> logins = new HashMap<>();

    private String login;
    private String password;

    static {
        for (User user : User.values()) {
            logins.put(user.getLogin(), user);
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static User getUser(String value) {
        return logins.get(value);
    }
}

