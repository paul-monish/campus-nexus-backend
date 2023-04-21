package edu.in.mckvie.CampusNexus.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private final HttpMessageConverter<Object> encryptionConverter;
    private final HttpMessageConverter<Object> decryptionConverter;

    public AppConfig(HttpMessageConverter<Object> encryptionConverter, HttpMessageConverter<Object> decryptionConverter) {
        this.encryptionConverter = encryptionConverter;
        this.decryptionConverter = decryptionConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(encryptionConverter);
        converters.add(decryptionConverter);
    }

}