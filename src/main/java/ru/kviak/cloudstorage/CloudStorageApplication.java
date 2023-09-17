package ru.kviak.cloudstorage;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaAuditing
@SpringBootApplication
public class CloudStorageApplication {
    @Value( "${minio.access.key}" )
    private String accessKey;
    @Value("${minio.secret.key}")
    private String secretKey;
    @Value("${minio.endpoint}")
    private String endPoint;

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class, args);
    }

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                        .endpoint(endPoint)
                        .credentials(accessKey, secretKey)
                        .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
