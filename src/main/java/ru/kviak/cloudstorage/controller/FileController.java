package ru.kviak.cloudstorage.controller;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kviak.cloudstorage.dto.UserFileDto;
import ru.kviak.cloudstorage.util.JwtTokenUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final MinioClient minioClient;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/file") // add file to bucket
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String fileUploadStatus;
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("cloud-storage")
                            .object(file.getOriginalFilename()) // Set the object name as the original filename
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

    @GetMapping("/file/{path:.+}") // get one file from bucket
    public ResponseEntity<Resource> downloadFile(@PathVariable("path") String filename) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("cloud-storage")
                        .object(filename)
                        .build())) {
            // Read data from stream
            byte[] data = IOUtils.toByteArray(stream);
            ByteArrayResource resource = new ByteArrayResource(data);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/file") // get all files from bucket
    public List<UserFileDto> getFiles(HttpServletRequest request) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {


        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        System.out.println(token);
        System.out.println(jwtTokenUtils.getUsername(token));

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket("cloud-storage")
                        .build());

        List<UserFileDto> files = new ArrayList<>();
        for (Result<Item> result : results) {
            Item item = result.get();
            files.add(new UserFileDto(item.objectName(), "http://localhost:8080/file/"+item.objectName().replace(" ", "%20"))); // Говно переделывать
        }
        return files;
    }
    @GetMapping("/folder") // Get all files from folder kviak/
    public List<UserFileDto> goida() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String folder = "kviak/";

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket("cloud-storage")
                        .prefix(folder)
                        .build());
        // Перебираем объекты и обрабатываем их
        System.out.println("We are in prefix folder.");
        for (Result<Item> result : results) {
            Item item = result.get();
            System.out.println("Имя файла: " + item.objectName());
        }
        return null;
    }
}
