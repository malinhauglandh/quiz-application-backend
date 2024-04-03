package org.ntnu.idi.idatt2105.project.controller;

import org.ntnu.idi.idatt2105.project.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class FileUploadController {

  private final FileStorageService fileStorageService;

  @Autowired
  public FileUploadController(FileStorageService fileStorageService) {
      this.fileStorageService = fileStorageService;
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    String fileName = fileStorageService.storeFile(file);
    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/uploads/")
            .path(fileName)
            .toUriString();

    return ResponseEntity.ok("File uploaded successfully: " + fileDownloadUri);
  }

  @GetMapping("/uploads")
  public ResponseEntity<String> getUploads() {
    return ResponseEntity.ok("Uploads");
  }

  @GetMapping("/uploads/{fileName:.+}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
      byte[] file = fileStorageService.loadFile(fileName);
      return ResponseEntity.ok()
              .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
              .body(file);
  }

  @GetMapping("delete/{fileName:.+}")
  public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
      return ResponseEntity.ok("File deleted successfully: " + fileName);
  }
}

