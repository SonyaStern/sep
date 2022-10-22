package com.kth.mse.sep.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SepConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
