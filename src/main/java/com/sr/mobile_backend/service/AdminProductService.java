package com.sr.mobile_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sr.mobile_backend.dtos.DeleteDto;
import com.sr.mobile_backend.dtos.ProductDto;
import com.sr.mobile_backend.entity.Admin;
import com.sr.mobile_backend.entity.Product;
import com.sr.mobile_backend.enums.Categories;
import com.sr.mobile_backend.repository.AdminRepo;
import com.sr.mobile_backend.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminProductService {


    ProductRepo productRepo;


    AdminRepo adminRepo;

    AdminProductService(ProductRepo productRepo , AdminRepo adminRepo){
        this.productRepo = productRepo;
        this.adminRepo = adminRepo;
    }

    //for admin
    //pass
    public ProductDto saveProduct(MultipartFile file,String json) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto dto = objectMapper.readValue(json,ProductDto.class);
        if(dto!=null) {
            if (authentication != null && file != null) {
                String fullPath = "use ur file path"
                        + file.getOriginalFilename();
                Path path = Paths.get(fullPath);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                String adminName = getCurrentAdminName(authentication);
                Optional<Admin> admin = adminRepo.findByName(adminName);


                if (admin.isPresent()) {
                    if (categoryExists(dto.getCategory())) {

                        Product product = Product.builder()
                                .productName(dto.getProductName())
                                .productPrice(dto.getProductPrice())
                                .category(Categories.valueOf(dto.getCategory()))
                                .admin(admin.get())
                                .available(dto.getAvailable())
                                .warranty(dto.getWarranty())
                                .discount(dto.getDiscount())
                                .productImageName(file.getOriginalFilename())
                                .build();

                        productRepo.save(product);

                        return ProductDto.builder()
                                .id(product.getId())
                                .productName(dto.getProductName())
                                .productPrice(dto.getProductPrice())
                                .category(dto.getCategory())
                                .vendor(admin.get().getOrgName())
                                .available(dto.getAvailable())
                                .warranty(dto.getWarranty())
                                .discount(dto.getDiscount())
                                .productImageName(file.getOriginalFilename())
                                .build();
                    }
                }

            }
            }
        return ProductDto.builder()
                .id(null)
                .productName(null)
                .productPrice(null)
                .category(null)
                .vendor(null)
                .available(null)
                .warranty(null)
                .discount(null)
                .productImageName(null)
                .build();
    }

    //pass
    public ProductDto updateProduct(MultipartFile file,String json) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto dto = objectMapper.readValue(json,ProductDto.class);
        System.out.println(dto.getId());
        if(dto.getId()!=null) {
            if (authentication != null && file != null) {
                String fullPath = "use ur file path"
                        + file.getOriginalFilename();
                Path path = Paths.get(fullPath);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                String adminName = getCurrentAdminName(authentication);
                Optional<Admin> admin = adminRepo.findByName(adminName);


                if (admin.isPresent()) {
                    System.out.println("admin is here");
                    System.out.println(dto.getCategory());
                    System.out.println(dto.getId());

                    if (categoryExists(dto.getCategory())) {
                        System.out.println(dto.getCategory());
                        System.out.println(dto.getId());
                        Product product = Product.builder()
                                .id(dto.getId())
                                .productName(dto.getProductName())
                                .productPrice(dto.getProductPrice())
                                .category(Categories.valueOf(dto.getCategory()))
                                .admin(admin.get())
                                .available(dto.getAvailable())
                                .warranty(dto.getWarranty())
                                .discount(dto.getDiscount())
                                .productImageName(file.getOriginalFilename())
                                .build();

                        productRepo.save(product);

                        return ProductDto.builder()
                                .id(product.getId())
                                .productName(dto.getProductName())
                                .productPrice(dto.getProductPrice())
                                .category(dto.getCategory())
                                .vendor(admin.get().getOrgName())
                                .available(dto.getAvailable())
                                .warranty(dto.getWarranty())
                                .discount(dto.getDiscount())
                                .productImageName(file.getOriginalFilename())
                                .build();
                    }
                }

            }
        }
        return ProductDto.builder()
                .id(null)
                .productName(null)
                .productPrice(null)
                .category(null)
                .vendor(null)
                .available(null)
                .warranty(null)
                .discount(null)
                .productImageName(null)
                .build();
    }

    //pass
    public Map<String,String> deleteProduct(DeleteDto dto) throws Exception{
        Map<String,String> response = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            String adminName = getCurrentAdminName(authentication);
            Optional<Admin> admin = adminRepo.findByName(adminName);
            if(admin.isPresent()){
                    if(dto.getId()!=null){
                        productRepo.deleteById(dto.getId());
                        response.put("delete","success");
                        return response;
                    }
                    else {
                        response.put("delete","failed");
                    }
                }
        }
        response.put("delete","failed");
        return response;
    }

    private String getCurrentAdminName(Authentication authentication) {
        return authentication.getName();
    }

    private Boolean categoryExists(String value) {
        return Arrays.stream(Categories.values())
                .anyMatch(category -> category.name().equalsIgnoreCase(value));
    }
}
