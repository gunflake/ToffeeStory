package com.toffeestory.backend.account;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @PostMapping(path = "/secured")
    public String test(){

        return "secured";
    }

    @GetMapping("/me")
    public String currentUser(@AuthenticationPrincipal UserDetails userDetails){
        String result = "username : "+ userDetails.getUsername();
        return result;
    }
}
