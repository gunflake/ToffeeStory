package com.toffeestory.backend.post;

import com.toffeestory.backend.exception.InvalidImageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.ResponseEntity.created;

@Slf4j
@RestController
@RequestMapping("/api")
public class FileUploadController {

    @PostMapping("/image")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile multipartFile) {
        log.info(multipartFile.getOriginalFilename());

        Path fileNameAndPath = Paths.get("./images/",multipartFile.getOriginalFilename());
        try{
            Files.write(fileNameAndPath, multipartFile.getBytes());
        }catch (IOException e){
            throw new InvalidImageException("이미지 업로드에 실패했습니다.");
        }

        return created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/api/image/{filename}")
                .buildAndExpand("multipartFile.getOriginalFilename")
                .toUri())
                .build();
    }
}
