package com.epam.billing.utils;
import java.util.regex.Pattern;

public class ValidationUtil {
    private ValidationUtil() {}

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)" +
            "+[a-zA-Z]{2,7}$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        if (password == null)
            return false;
        return pattern.matcher(password).matches();
    }
}
