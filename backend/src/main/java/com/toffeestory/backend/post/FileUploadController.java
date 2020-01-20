package com.toffeestory.backend.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @PostMapping("/api/upload/image")
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        logger.info(multipartFile.getOriginalFilename());

        Path fileNameAndPath = Paths.get("./images/",multipartFile.getOriginalFilename());
        try{
            Files.write(fileNameAndPath, multipartFile.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }

        return "Success Upload Image";
    }
}
