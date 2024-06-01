package org.example.e_learningback.controller;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileServiceController {

    private final FileService fileService;
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("image") MultipartFile multipartFile
    ) throws IOException {
        return fileService.uploadFile(multipartFile);
    }
}
