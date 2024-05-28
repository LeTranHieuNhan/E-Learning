package org.example.e_learningback.service.impl;

import com.cloudinary.Cloudinary;
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
public class FileServiceImpl implements FileService {
    private final Cloudinary cloudinary;
    private final FileRepository fileRepository;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException, IOException {
        String source = cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
        File file = new File(null, source);
        File newFile = fileRepository.save(file);

        return newFile.getSource();
    }
}
