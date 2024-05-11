package org.example.e_learningback.config;


import lombok.RequiredArgsConstructor;
import org.example.e_learningback.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserRepository userRepository;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
