package ru.kviak.cloudstorage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    @PostMapping("/file")
    public String uploadFile(@RequestParam("file") MultipartFile file){

        String filePath = System.getProperty("user.dir") + "/Uploads" + File.separator + file.getOriginalFilename();
        String fileUploadStatus;
        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(file.getBytes());
            fout.close();
            fileUploadStatus = "File Uploaded Successfully";
        }
        catch (Exception e) {
            e.printStackTrace();
            fileUploadStatus =  "Error in uploading file: " + e;
        }
        return fileUploadStatus;
    }

    @GetMapping("/file")
    public String[] getFiles()
    {
        String folderPath = System.getProperty("user.dir") +"/Uploads";
        File directory= new File(folderPath);
        return directory.list();
    }

    @GetMapping("/file/{path:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable("path") String filename) throws FileNotFoundException {

        String fileUploadPath = System.getProperty("user.dir") +"/Uploads";
        String[] filenames = this.getFiles();
        boolean contains = Arrays.asList(filenames).contains(filename);
        if(!contains) {
            return new ResponseEntity<>("FIle Not Found",HttpStatus.NOT_FOUND);
        }
        String filePath = fileUploadPath+File.separator+filename;
        File file = new File(filePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
}
