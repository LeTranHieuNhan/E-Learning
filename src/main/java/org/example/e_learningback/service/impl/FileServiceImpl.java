package org.example.e_learningback.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.example.e_learningback.entity.File;
import org.example.e_learningback.repository.FileRepository;
import org.example.e_learningback.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class FileServiceImpl implements FileService {
    private final Cloudinary cloudinary;
    private final FileRepository fileRepository;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String fileType = multipartFile.getContentType();
        String resourceType = "auto"; // Let Cloudinary determine the resource type

        if (fileType != null && fileType.startsWith("video")) {
            resourceType = "video";
        } else if (fileType != null && fileType.startsWith("image")) {
            resourceType = "image";
        } else {
            resourceType = "raw"; // Default to raw for other file types
        }

        // Upload file to Cloudinary
        Map<String, Object> uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", resourceType,
                        "public_id", UUID.randomUUID().toString()
                ));

        // Extract the URL of the uploaded file
        String source = (String) uploadResult.get("secure_url"); // Using secure_url for HTTPS

        if (source == null) {
            source = (String) uploadResult.get("url");
        }

        if (source == null) {
            throw new IOException("Failed to retrieve URL from Cloudinary response.");
        }

        // Save file URL in the database
        File file = new File(null, source, null); // Adjust the entity constructor parameters as needed
        File newFile = fileRepository.save(file);

        // Return the file URL
        System.out.println("File URL: " + (String) uploadResult.get("url"));
        return (String) uploadResult.get("url");
    }
}
