package com.toffeestory.backend.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class SampleController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/api/test")
    public ResponseEntity<String> test(){
        return ok("success");
    }

    @GetMapping("/api/test/email")
    public ResponseEntity simpleMailTest() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("gunflake09@naver.com");

        msg.setSubject("[ToffeeStudy] Password Reset");

        String message = "You're receiving this e-mail because you or someone else has requested a password reset for your user account at . \n\n"
                + "Click the link below to reset your password: \n"
                + "https://leetcode.com/accounts/password/reset/key/pbg0-5ah-0f6e04e73569a64e9c7d/ \n\n"
                + "If you did not request a password reset you can safely ignore this email.";
        msg.setText(message);

        javaMailSender.send(msg);
        return ok("success");
    }
}
