// app/src/main/java/sellFlower/app/utils/ValidationUtils.java
package sellFlower.app.utils;

import android.util.Patterns;

public class ValidationUtils {
    public static boolean isValidEmail(String email) {
        return email != null && !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one number
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        return password != null && password.matches(passwordRegex);
    }

    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2;
    }

    public static String getPasswordErrorMessage() {
        return "Password must be at least 8 characters long and contain at least one number";
    }
}