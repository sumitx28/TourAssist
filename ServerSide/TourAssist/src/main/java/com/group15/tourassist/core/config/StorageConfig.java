package com.group15.tourassist.core.config;

import com.cloudinary.Cloudinary;
import com.group15.tourassist.core.utils.ConstantUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class StorageConfig {

    @Value("${storage.cloud-name}")
    private String cloudName;

    @Value("${storage.access-key}")
    private String accessKey;


    @Value("${storage.secret-key}")
    private String secretKey;


    /**
     * @return Returns initialized Cloudinary with API key and secret.
     */
    @Bean
    public Cloudinary getCloudinary() {
        Map config = new HashMap();
        config.put(ConstantUtils.CLOUD_NAME, cloudName);
        config.put(ConstantUtils.CLOUD_API_KEY, accessKey);
        config.put(ConstantUtils.CLOUD_API_SECRET, secretKey);
        config.put(ConstantUtils.CLOUD_SECURE, true);

        return new Cloudinary(config);
    }
}
