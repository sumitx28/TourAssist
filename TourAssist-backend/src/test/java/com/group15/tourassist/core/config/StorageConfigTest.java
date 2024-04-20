package com.group15.tourassist.core.config;

import com.cloudinary.Cloudinary;
import com.group15.tourassist.core.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StorageConfigTest {

    @InjectMocks
    private StorageConfig storageConfig;

    private Cloudinary cloudinary;


    @BeforeEach
    public void setup() {
        Map config = new HashMap();
        config.put(ConstantUtils.CLOUD_NAME, "abc");
        config.put(ConstantUtils.CLOUD_API_KEY, "key");
        config.put(ConstantUtils.CLOUD_API_SECRET, "secret");
        config.put(ConstantUtils.CLOUD_SECURE, true);
        cloudinary = new Cloudinary(config);
    }

    @Test
    void getCloudinary() {
        // Act
        Cloudinary cloudinary1 = storageConfig.getCloudinary();

        // Assert
        assertEquals(cloudinary.config.secure, cloudinary1.config.secure);
    }
}