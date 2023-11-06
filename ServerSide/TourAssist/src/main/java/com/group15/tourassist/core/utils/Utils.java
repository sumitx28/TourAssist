package com.group15.tourassist.core.utils;

import org.apache.logging.log4j.util.Strings;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.group15.tourassist.core.utils.ConstantUtils.*;


/**
 * Util class generic validations and utility methods
 */
public class Utils {


    /**
     * @param email Email to validate
     * @return true if proper, otherwise false
     */
    public static boolean validateEmail(String email) {
        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * @param password password to validate
     * @return true if length is at least 8 characters
     */
    public static boolean validatePassword(String password) {
        if(Strings.isEmpty(password)) {
            return false;
        }

        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    /**
     * @param mobile input mobile number
     * @return true if validation pass
     */
    public static boolean validateMobile(String mobile) {
        if(Strings.isEmpty(mobile)) {
            return false;
        }

        if(mobile.matches("\\d+") && mobile.length() == MOBILE_LENGTH) {
            return true;
        }
        return false;
    }


    /**
     * @param name input name
     * @return true if not null or empty
     */
    public static boolean validateName(String name) {
        if(Strings.isEmpty(name)) {
            return false;
        }

        return true;
    }


    /**
     * @param instant start time
     * @return end time by adding 365 days
     */
    public static Instant getEndOfTime(Instant instant) {
        return instant.plus(DAYS_IN_YEAR, ChronoUnit.DAYS);
    }

}
