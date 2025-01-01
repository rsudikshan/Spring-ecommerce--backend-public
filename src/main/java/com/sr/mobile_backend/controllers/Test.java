package com.sr.mobile_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/cat")
    public String test(){
        return "cat";
    }

    @GetMapping("/admin/test")
    public String test2(){
        return "hello";
    }
}
