package com.group15.tourassist.core.config.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class TokenGenerator {
    private static final int RANDOM_STRING_LENGTH=8;
    private static final Random RANDOM = new SecureRandom();

    public static String generateUniqueToken(){
        String randomString= generateRandomString(RANDOM_STRING_LENGTH);
        String uniqueToken=randomString+System.currentTimeMillis();
        byte[] encodedToken = Base64.getUrlEncoder().encode(uniqueToken.getBytes());
        return new String(encodedToken);
    }

    public static String generateRandomString(int length) {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder randomString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }
        return randomString.toString();
    };


}
