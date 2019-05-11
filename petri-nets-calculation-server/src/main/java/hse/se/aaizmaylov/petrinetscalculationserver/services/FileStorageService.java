package hse.se.aaizmaylov.petrinetscalculationserver.services;

import hse.se.aaizmaylov.petrinetscalculationserver.properties.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;
    private static final String RES_SUFFIX = "_res";

    private final AtomicLong counter = new AtomicLong(0);

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath("file" + counter.incrementAndGet());

        try {
            Path target = fileStorageLocation.resolve(fileName);

            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new StoringFileException(e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                System.err.println(fileName);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String generateResultPath(String fileName) {
        return fileName + RES_SUFFIX;
    }

    public String getPathToFile(String fileName) {
        return fileStorageLocation.resolve(fileName).normalize().toString();
    }
}
