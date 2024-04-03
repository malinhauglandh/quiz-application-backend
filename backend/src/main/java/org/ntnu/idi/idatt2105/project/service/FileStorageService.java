package org.ntnu.idi.idatt2105.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileStorageService {

  @Value("${file.upload-dir}")
  private String uploadDir;

  public String storeFile(MultipartFile file) {
    try {
      Path fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
      Files.createDirectories(fileStorageLocation);

      String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
      Path targetLocation = fileStorageLocation.resolve(fileName);

      Files.copy(file.getInputStream(), targetLocation);

      return fileName;
    } catch (IOException ex) {
      throw new IllegalArgumentException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
    }
  }

    public byte[] loadFile(String fileName) {
        try {
        Path fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path filePath = fileStorageLocation.resolve(fileName).normalize();
        return Files.readAllBytes(filePath);
        } catch (IOException ex) {
        throw new IllegalArgumentException("Could not load file " + fileName + ". Please try again!", ex);
        }
    }
}
