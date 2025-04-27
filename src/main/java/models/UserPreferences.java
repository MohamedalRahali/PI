package models;

import java.io.*;
import java.util.Properties;
import java.time.LocalDateTime;

public class UserPreferences {
    private static final String PREFS_FILE = "user_preferences.properties";
    private static final Properties props = new Properties();

    static {
        loadPreferences();
    }

    private static void loadPreferences() {
        File file = new File(PREFS_FILE);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                props.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void savePreferences() {
        try (FileOutputStream fos = new FileOutputStream(PREFS_FILE)) {
            props.store(fos, "User Preferences");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveRememberMe(String email, String password, LocalDateTime expiryDate) {
        props.setProperty("remembered_email", email);
        props.setProperty("remembered_password", password);
        props.setProperty("expiry_date", expiryDate.toString());
        savePreferences();
    }

    public static void clearRememberMe() {
        props.remove("remembered_email");
        props.remove("remembered_password");
        props.remove("expiry_date");
        savePreferences();
    }

    public static boolean hasValidRememberMe() {
        String expiryDateStr = props.getProperty("expiry_date");
        if (expiryDateStr != null) {
            LocalDateTime expiryDate = LocalDateTime.parse(expiryDateStr);
            return LocalDateTime.now().isBefore(expiryDate);
        }
        return false;
    }

    public static String getRememberedEmail() {
        return props.getProperty("remembered_email");
    }

    public static String getRememberedPassword() {
        return props.getProperty("remembered_password");
    }
}
