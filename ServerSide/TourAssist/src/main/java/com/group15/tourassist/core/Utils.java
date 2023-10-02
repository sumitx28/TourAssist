package com.group15.tourassist.core;

import org.apache.logging.log4j.util.Strings;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Utility methods
public class Utils {

    public static boolean validateEmail(String email) {
        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // length = 8 char at least
    public static boolean validatePassword(String password) {
        if(Strings.isEmpty(password)) {
            return false;
        }

        return password.length() >= 8;
    }
}
