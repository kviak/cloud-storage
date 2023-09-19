package ru.kviak.cloudstorage.service;

import io.minio.*;
import io.minio.messages.Item;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kviak.cloudstorage.dto.UserFileDto;
import ru.kviak.cloudstorage.exception.MinioNotFoundException;
import ru.kviak.cloudstorage.util.JwtTokenUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileMinioService {
    private final MinioClient minioClient;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;
    @Value("${application.url}")
    private String url;

    public void createFolderForNewUser(String  email, String username) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("cloud-storage")
                            .object(email + "/" + username + "/").stream(
                            new ByteArrayInputStream(new byte[] {}), 0, -1)
                            .build());
        }
        catch (Exception e){
            e.printStackTrace();
            throw new MinioNotFoundException("Ошибка при создании, директории нового пользователя.");
        }
    }

    @SneakyThrows
    public List<UserFileDto> getAllUserFile(String token) {
        String username = jwtTokenUtils.getUsername(token);
        String folder = userService.findByUsername(username).get().getEmail() + "/";

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket("cloud-storage")
                        .prefix(folder)
                        .build());
        List<UserFileDto> files = new ArrayList<>();
        for (Result<Item> result : results) {
            Item item = result.get();
            files.add(new UserFileDto(item.objectName().replace(folder, ""), url+"file/"+item.objectName().replace(" ", "%20").replace(folder, "")));
        }
        return files;
    }

    public String uploadUserFile(String token, MultipartFile file) {
        String fileUploadStatus;
        String folder = userService.findByUsername(jwtTokenUtils.getUsername(token)).get().getEmail() + "/";
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("cloud-storage")
                            .object(folder + file.getOriginalFilename()) // Set the object name as the original filename
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build());
            fileUploadStatus = "File Uploaded Successfully";
        }
        catch (Exception e) {
            e.printStackTrace();
            fileUploadStatus =  "Error in uploading file: " + e;
        }
        return fileUploadStatus;
    }

    public Resource getFile(String token, String fileName) {
        String folder =userService.findByUsername(jwtTokenUtils.getUsername(token)).get().getEmail() + "/";

        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("cloud-storage")
                        .object(folder + fileName)
                        .build())) {
            byte[] data = IOUtils.toByteArray(stream);
            return new ByteArrayResource(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getToken(HttpServletRequest request){
        return request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
    }
}