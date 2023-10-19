package com.group15.tourassist.core.utils;

import org.apache.logging.log4j.util.Strings;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    // mobile = 10 char length.
    public static boolean validateMobile(String mobile) {
        if(Strings.isEmpty(mobile)) {
            return false;
        }

        if(mobile.matches("\\d+") && mobile.length() == 10) {
            return true;
        }
        return false;
    }

    // name validation
    public static boolean validateName(String name) {
        if(Strings.isEmpty(name)) {
            return false;
        }

        return true;
    }

    // Util method to get the future end-dates.
    public static Instant getEndOfTime(Instant instant) {
        return instant.plus(365, ChronoUnit.DAYS);
    }

}
