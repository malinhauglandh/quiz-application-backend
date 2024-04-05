package org.ntnu.idi.idatt2105.project.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import org.ntnu.idi.idatt2105.project.entity.quiz.Quiz;
import org.ntnu.idi.idatt2105.project.repository.quiz.QuizRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/** Service class for storing and loading files */
@Service
public class FileStorageService {

    /** The path where the files are stored */
    private final Path fileStorageLocation;

    /** The repository for Quiz */
    private final QuizRepository quizRepository;

    /**
     * Constructor for FileStorageService
     *
     * @param uploadDir The directory where the files are stored
     * @param quizRepository The repository for Quiz
     */
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

    /**
     * Store a file. The file is stored in the directory specified in the application.properties
     * file.
     *
     * @param file The file to store
     * @param quizId The id of the quiz
     */
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

    /**
     * Load a file from the file storage location. If the file does not exist, a default image is
     * returned.
     *
     * @param fileName The name of the file
     * @return The file as a byte array
     */
    public ResponseEntity<byte[]> loadFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok().body(Files.readAllBytes(filePath));
            } else {
                Path defaultImagePath = this.fileStorageLocation.resolve("default.png").normalize();
                // location
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
