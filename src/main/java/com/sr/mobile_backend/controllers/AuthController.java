package com.sr.mobile_backend.controllers;

import com.sr.mobile_backend.dtos.AdminAuthDto;
import com.sr.mobile_backend.dtos.AdminRegisterDto;
import com.sr.mobile_backend.dtos.AuthDto;
import com.sr.mobile_backend.dtos.RegisterDto;
import com.sr.mobile_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {


    AuthService service;

    AuthController(AuthService authService){
        this.service = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody RegisterDto dto){
        return ResponseEntity.ok().body(service.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@RequestBody AuthDto dto){
        return ResponseEntity.ok(service.loginValidate(dto));
    }

    @PostMapping("/adminRegister")
    public ResponseEntity<Map<String,String>> adminRegister(@RequestBody AdminRegisterDto dto){
        return ResponseEntity.ok().body(service.adminRegister(dto));
    }

    @PostMapping("/adminLogin")
    public ResponseEntity<Map<String,String>> adminLogin(@RequestBody AdminAuthDto dto){
        return ResponseEntity.ok().body(service.adminLogin(dto));
    }



}
