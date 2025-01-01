package com.sr.mobile_backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sr.mobile_backend.dtos.DeleteDto;
import com.sr.mobile_backend.dtos.ProductDto;
import com.sr.mobile_backend.service.AdminProductService;
import com.sr.mobile_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminOnly {


    AdminProductService service;

    AdminOnly(AdminProductService service){
        this.service = service;
    }

    @PostMapping(value ="/saveProduct")
    public ResponseEntity<ProductDto> addProduct(@RequestParam("file") MultipartFile file
            ,@RequestParam("json") String json)throws Exception{
        return  ResponseEntity.ok().body(service.saveProduct(file,json));
    }

    @PostMapping(value ="/updateProduct")
    public ResponseEntity<ProductDto> updateProduct(@RequestParam("file") MultipartFile file
            ,@RequestParam("json") String json)throws Exception{
        return ResponseEntity.ok().body(service.updateProduct(file,json));

    }

    @PostMapping(value ="/deleteProduct")
    public ResponseEntity<Map<String,String>> deleteProduct(@RequestBody DeleteDto dto)throws Exception{
        return ResponseEntity.ok().body(service.deleteProduct(dto));
    }
}
