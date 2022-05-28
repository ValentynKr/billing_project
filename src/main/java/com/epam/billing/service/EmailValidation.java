package com.epam.billing.service;
import java.util.regex.Pattern;

public class EmailValidation {
    private EmailValidation() {}

    public static boolean isValid(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)" +
                "+[a-zA-Z]{2,7}$");
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }
}
