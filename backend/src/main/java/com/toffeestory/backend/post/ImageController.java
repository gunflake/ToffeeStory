package com.toffeestory.backend.post;

import com.toffeestory.backend.exception.NotFoundImageException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @GetMapping(path = "/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity getImageName(@PathVariable("imageName") String imageName) throws IOException {
        //TODO : postName으로 받아서 처리하기
        InputStream in = null;
        try{
            in = new BufferedInputStream(
                    new FileInputStream("./images/" + imageName));
        }catch (Exception e){
            throw new NotFoundImageException(imageName);
        }

        return ok(IOUtils.toByteArray(in));
    }
}
