package ru.kviak.cloudstorage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kviak.cloudstorage.dto.FolderDto;
import ru.kviak.cloudstorage.dto.UserFileDto;
import ru.kviak.cloudstorage.service.FileMinioService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileMinioService fileMinioService;

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
        return ResponseEntity.ok(fileMinioService.uploadUserFile(request, files));
    }

    @GetMapping("/file/{path}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("path") String fileName, HttpServletRequest request) {
        ByteArrayResource file = fileMinioService.getFile(request, fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .body(file);

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

    @GetMapping("/folder")
    public ResponseEntity<List<FolderDto>> getUserFolder(HttpServletRequest request){ // Change to folderDto
        return ResponseEntity.ok(fileMinioService.getUserFolder(request));
    }

    @DeleteMapping("/folder/{path}")
    public ResponseEntity<?> deleteFolder(@PathVariable("path") String folderName, HttpServletRequest request) {
        if (fileMinioService.deleteFolder(request, folderName)) return ResponseEntity.noContent().build();
        else return ResponseEntity.badRequest().body("Folder not found!");
    }

    @GetMapping("/folder/{path}")
    public ResponseEntity<?> getFolderFiles(@PathVariable("path") String folderName, HttpServletRequest request){
        return ResponseEntity.ok(fileMinioService.getFolder(request, folderName));
    }
}
