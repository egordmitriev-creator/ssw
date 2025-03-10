package com.example.MyPageApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {
    @GetMapping("/profile")
    public String getProfile(){
        return "profile.html";
    }
}
