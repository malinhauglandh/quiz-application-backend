package org.ntnu.idi.idatt2105.project.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;
    private final QuizRepository quizRepository;

    public FileStorageService(
            @Value("${file.upload-dir}") String uploadDir, QuizRepository quizRepository) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.quizRepository = quizRepository;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new IllegalArgumentException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public void storeFile(MultipartFile file, int quizId) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Quiz quiz =
                    quizRepository
                            .findById((long) quizId)
                            .orElseThrow(
                                    () -> new RuntimeException("Quiz not found with id " + quizId));
            quiz.setMultimedia(fileName);
            quizRepository.save(quiz);
        } catch (IOException ex) {
            throw new IllegalArgumentException(
                    "Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public ResponseEntity<byte[]> loadFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok().body(Files.readAllBytes(filePath));
            } else {
                Path defaultImagePath = this.fileStorageLocation.resolve("default.png").normalize(); // Ensure you have a default.png image in your storage location
                Resource defaultImageResource = new UrlResource(defaultImagePath.toUri());
                if (defaultImageResource.exists()) {
                    return ResponseEntity.ok().body(Files.readAllBytes(defaultImagePath));
                } else {
                    throw new IllegalArgumentException("Default image file not found");
                }
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("File not found " + fileName, ex);
        }
    }
}
