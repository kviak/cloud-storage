package ru.kviak.cloudstorage.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.kviak.cloudstorage.service.FileMinioService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestFileController {

    @Mock
    private FileMinioService fileMinioService;

    private FileController fileController;

    @BeforeEach
    public void setup() {
        fileController = new FileController(fileMinioService);
    }

    @Test
    public void testUploadFile() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "File content".getBytes(StandardCharsets.UTF_8));

        when(fileMinioService.uploadUserFile(any(HttpServletRequest.class), any(MultipartFile[].class))).thenReturn("Uploaded");

        ResponseEntity<String> response = fileController.uploadFile(new MultipartFile[]{file}, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Uploaded", response.getBody());
    }

    @Test
    public void testDownloadFile() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        ByteArrayResource resource = new ByteArrayResource("File content".getBytes(StandardCharsets.UTF_8));
        when(fileMinioService.getFile(any(HttpServletRequest.class), any(String.class))).thenReturn(resource);

        ResponseEntity<Resource> response = fileController.downloadFile("test.txt", request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("attachment; filename=test.txt", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
    }

    @Test
    public void testDeleteFile_FileExists() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        String fileName = "test.txt";

        when(fileMinioService.deleteFile(any(HttpServletRequest.class), any(String.class))).thenReturn(true);

        ResponseEntity<?> response = fileController.deleteFile(fileName, request);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteFile_FileNotFound() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        String fileName = "non_existent.txt";

        when(fileMinioService.deleteFile(any(HttpServletRequest.class), any(String.class))).thenReturn(false);

        ResponseEntity<?> response = fileController.deleteFile(fileName, request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("File not found!", response.getBody());
    }

}
