package com.toffeestory.backend.post;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @GetMapping(path = "/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImageName(@PathVariable("imageName") String imageName) throws IOException {
        //TODO : postName으로 받아서 처리하기
        InputStream in = new BufferedInputStream(
                new FileInputStream("./images/" + imageName));
        return IOUtils.toByteArray(in);
    }
}
