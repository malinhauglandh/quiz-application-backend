package org.ntnu.idi.idatt2105.project.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Value;
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
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Update quiz entity with the file information
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
}
