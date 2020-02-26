package com.toffeestory.backend.sample;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class SampleController {

    @GetMapping("/api/test")
    public ResponseEntity<String> test(){
        return ok("success");
    }
}
