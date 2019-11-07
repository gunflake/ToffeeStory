package com.toffeestory.backend.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConroller {

    @GetMapping("/hello")
    public String getHello(){
        return "hello";
    }
}
