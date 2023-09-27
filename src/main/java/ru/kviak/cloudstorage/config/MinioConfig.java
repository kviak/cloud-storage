package ru.kviak.cloudstorage.config;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@Configuration
@ConfigurationProperties("minio")
public class MinioConfig {
    private String user;
    private String password;
    private String endPoint;
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(endPoint)
                .credentials(user, password)
                .build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
