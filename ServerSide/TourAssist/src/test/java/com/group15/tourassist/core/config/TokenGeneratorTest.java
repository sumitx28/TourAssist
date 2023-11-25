package com.group15.tourassist.core.config;

import com.group15.tourassist.core.config.service.TokenGenerator;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenGeneratorTest {
    @Test
    public void testGenerateUniqueToken() {
        String uniqueToken = TokenGenerator.generateUniqueToken();

        assertNotNull(uniqueToken);
        assertFalse(uniqueToken.isEmpty());
        //assertTrue(uniqueToken.matches("[A-Za-z0-9_\\-]+[0-9]+"));
    }




    @Test
    public void testGenerateRandomString() {
        int length = 10;
        TokenGenerator tokenGenerator = new TokenGenerator();

        String randomString = tokenGenerator.generateRandomString(length);

        assertEquals(length, randomString.length());
        assertTrue(randomString.matches("[0-9a-zA-Z]+"));
    }
}
