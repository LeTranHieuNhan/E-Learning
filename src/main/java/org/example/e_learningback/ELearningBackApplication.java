package org.example.e_learningback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ELearningBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ELearningBackApplication.class, args);
    }





}
