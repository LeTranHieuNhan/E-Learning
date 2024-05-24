package org.example.e_learningback.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
@Configuration

public class CloudinaryConfig {
    private final String CLOUD_NAME = "dqemenkwp";
    private final String API_KEY = "631248954428776";
    private final String API_SECRET = "wsvXPkkNudK3GcuuQzOdpVl7woY";
    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);

        return new Cloudinary(config);
    }
}
