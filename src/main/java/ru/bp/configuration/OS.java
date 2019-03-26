package ru.bp.configuration;

public enum OS {
    WINDOWS, LINUX, MACOS;


    public static OS getHostOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return WINDOWS;
        } else if (os.contains("mac")) {
            return MACOS;
        } else {
            return LINUX;
        }
    }

    public static OS getOSByName(String name) {
        name = name.toUpperCase();
        switch (name) {
            case "LINUX":
                return LINUX;
            case "MACOS":
                return MACOS;
            case "WINDOWS":
            default:
                return WINDOWS;
        }
    }
}
