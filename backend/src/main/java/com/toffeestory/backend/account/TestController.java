package com.toffeestory.backend.account;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @PostMapping(path = "/secured")
    public String test(){

        return "secured";
    }

    @GetMapping("/message")
    public String getText() {
        return "Hello world";
    }

    @GetMapping("/me")
    public String currentUser(@AuthenticationPrincipal UserDetails userDetails){
        String result = "username : "+ userDetails.getUsername();
        return result;
    }

    @GetMapping(path = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {
        InputStream in = new BufferedInputStream(
                new FileInputStream("./images/img_profile.jpeg"));
        return IOUtils.toByteArray(in);
    }

    @GetMapping(path = "/images/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImageName(@PathVariable("imageName") String imageName) throws IOException {
        //TODO : postName으로 받아서 처리하기
        InputStream in = new BufferedInputStream(
                new FileInputStream("./images/" + imageName));
        return IOUtils.toByteArray(in);
    }
}
