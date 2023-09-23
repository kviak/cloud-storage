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
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class FileController {

    private final FileMinioService fileMinioService;

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
        return ResponseEntity.ok(fileMinioService.uploadUserFile(request, files));
    }

    @GetMapping("/file/{path}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("path") String fileName, HttpServletRequest request) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .body(fileMinioService.getFile(request, fileName));
    }

    @GetMapping("/file")
    public ResponseEntity<List<UserFileDto>> getFiles(HttpServletRequest request){
        return ResponseEntity.ok(fileMinioService.getUserFiles(request));
    }

    @DeleteMapping("/file/{path}")
    public ResponseEntity<?> deleteFile(@PathVariable("path") String fileName, HttpServletRequest request) {
        if (fileMinioService.deleteFile(request, fileName)) return ResponseEntity.noContent().build();
            else return ResponseEntity.badRequest().body("File not found!");
    }

    @GetMapping("/admin/{username}")
    public ResponseEntity<List<UserFileDto>> getUserFileAdmin(@PathVariable("username") String username){
        return ResponseEntity.ok(fileMinioService.getUserFilesAdmin(username));
    }
}
