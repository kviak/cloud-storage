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
import ru.kviak.cloudstorage.exception.FileNotFoundException;
import ru.kviak.cloudstorage.exception.FileSizeExceedException;
import ru.kviak.cloudstorage.exception.MinioNotFoundException;
import ru.kviak.cloudstorage.exception.UserNotFoundException;
import ru.kviak.cloudstorage.util.jwt.JwtTokenUtils;

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

    public String uploadUserFile(String token, MultipartFile[] files) {
        String userDirectory = userService.findByUsername(jwtTokenUtils.getUsername(token)).get().getEmail() + "/";
        boolean isVip = jwtTokenUtils.getRoles(token).contains("ROLE_VIP");;

        try {
            for (MultipartFile file : files) {
                if ((file.getSize() > 10485760 && !isVip) || (isVip && file.getSize() > 20971520)) throw new FileSizeExceedException("");
                InputStream in = new ByteArrayInputStream(file.getBytes());
                String fileName = file.getOriginalFilename();

                minioClient.putObject(
                        PutObjectArgs
                                .builder()
                                .bucket("cloud-storage")
                                .object(userDirectory + fileName)
                                .stream(in, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }
            return "File successfully upload!";

        } catch (Exception e) {
            if (e.getClass() == MinioNotFoundException.class) throw new MinioNotFoundException("Failed upload file");
                else throw new FileSizeExceedException(e.getMessage());
        }
    }

    public Resource getFile(String token, String fileName) {
        String folder = userService.findByUsername(jwtTokenUtils.getUsername(token)).get().getEmail() + "/";

        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("cloud-storage")
                        .object(folder + fileName)
                        .build())) {
            byte[] data = IOUtils.toByteArray(stream);
            return new ByteArrayResource(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileNotFoundException("File not found exception!");
        }
    }

    public String getToken(HttpServletRequest request){
        return request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
    }

    public boolean deleteFile(HttpServletRequest request, String fileName) {
        String folder = userService.findByUsername(jwtTokenUtils.getUsername(getToken(request))).get().getEmail() + "/";
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket("cloud-storage")
                            .object(folder + fileName)
                            .build());
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public List<UserFileDto> getUserFiles(String token) {
        String username = jwtTokenUtils.getUsername(token);
        return getAllUserFiles(userService.findByUsername(username).get().getEmail());
    }

    public List<UserFileDto> getUserFilesAdmin(String username) {
        String email = userService.findByUsername(username).orElseThrow(UserNotFoundException::new).getEmail();
        return getAllUserFiles(email);
    }

    @SneakyThrows
    private List<UserFileDto> getAllUserFiles(String email) {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket("cloud-storage")
                        .prefix(email + "/")
                        .build());
        List<UserFileDto> files = new ArrayList<>();
        for (Result<Item> result : results) {
            Item item = result.get();
            files.add(new UserFileDto(item.objectName().replace(email + "/", ""), url+"file/"+item.objectName().replace(" ", "%20").replace(email + "/", "")));
        }
        return files;
    }
}
