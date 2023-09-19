package ru.kviak.cloudstorage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kviak.cloudstorage.dto.UserFileDto;
import ru.kviak.cloudstorage.service.FileMinioService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileMinioService fileMinioService;

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return ResponseEntity.ok(fileMinioService.uploadUserFile(fileMinioService.getToken(request), file));
    }

    @GetMapping("/file/{path:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("path") String fileName, HttpServletRequest request) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileMinioService.getFile(fileMinioService.getToken(request), fileName));
    }

    @GetMapping("/file")
    public ResponseEntity<List<UserFileDto>> getFiles(HttpServletRequest request){
        return ResponseEntity.ok(fileMinioService.getAllUserFile(fileMinioService.getToken(request)));
    }
}
