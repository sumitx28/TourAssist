package com.group15.tourassist.core.utils;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.group15.tourassist.core.utils.ConstantUtils.DAYS_IN_YEAR;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void validateEmailTest_NegativeCase() {
        // Arrange
        String actualEmail = "abc.com";

        // Act
        boolean isValid = Utils.validateEmail(actualEmail);

        // Assert
        assertFalse(isValid, actualEmail + " is a proper email id.");
    }

    @Test
    void validatePasswordTest_NegativeCase() {
        // Arrange
        String actualPassword = "abcd";

        // Act
        boolean isValid = Utils.validatePassword(actualPassword);

        // Assert
        assertFalse(isValid, actualPassword + " is valid password.");
    }

    @Test
    void validateMobileTest_PositiveCase() {
        // Arrange
        String actualMobile = "9824378667";

        // Act
        boolean isValid = Utils.validateMobile(actualMobile);

        // Assert
        assertTrue(isValid, actualMobile + " is not valid mobile number.");
    }

    @Test
    void validateNameTest_NegativeCase() {
        // Arrange
        String actualName = "";

        // Act
        boolean isValid = Utils.validateName(actualName);

        // Assert
        assertFalse(isValid, actualName + " is valid name.");
    }

    @Test
    void getEndOfTimeTest_PositiveCase() {
        // Arrange
        Instant now = Instant.parse("2023-01-01T00:00:00Z");

        // Act
        Instant endOfTime = Utils.getEndOfTime(now);

        // Assert
        assertEquals(Instant.parse("2024-01-01T00:00:00Z"), endOfTime);
    }
}