package ru.kviak.cloudstorage.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileMinioService {
    private final MinioClient minioClient;

    public void createFolderForNewUser(String  email, String username) throws IOException {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("cloud-storage")
                            .object(email + "/" + username + "/").stream(
                            new ByteArrayInputStream(new byte[] {}), 0, -1)
                            .build());
        }
        catch (Exception ignored) {}
    }
}
