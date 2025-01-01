package com.sr.mobile_backend.service;

import com.sr.mobile_backend.dtos.ProductDto;
import com.sr.mobile_backend.entity.Admin;
import com.sr.mobile_backend.entity.Product;
import com.sr.mobile_backend.repository.AdminRepo;
import com.sr.mobile_backend.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductService {

    ProductRepo repo;

    ProductService(ProductRepo repo){
        this.repo = repo;

    }

    //for clients
    public List<ProductDto> getAllProducts(){
        List<Product> products = repo.findAll();
        return products.stream()
                .map(this::dtoConverterHelper)
                .toList();
    }

    public ProductDto searchByProductName(String productName){
        Product product = repo.findByProductName(productName);
        return dtoConverterHelper(product);
    }

    public Boolean existsByName(String productName){
        return repo.existsByProductName(productName);
    }

    public List<ProductDto> getAllSimilarProducts(String productName){
        List<Product> products = repo.findByProductNameContaining(productName);
        return products.stream().map(this::dtoConverterHelper).toList();
    }

    public List<ProductDto> findInPriceRangeLessThanEqual(Integer price){
        List<Product> products = repo.findByProductPriceLessThanEqual(price);
        return products.stream().map(this::dtoConverterHelper).toList();
    }

    public List<ProductDto> findInPriceRangeGreaterThanEqual(Integer price){
        List<Product> products = repo.findByProductPriceGreaterThanEqual(price);
        return products.stream().map(this::dtoConverterHelper).toList();
    }

    public List<ProductDto> findByCategory(String category){
        List<Product> products = repo.findByCategory(category);
        return products.stream().map(this::dtoConverterHelper).toList();
    }

    public List<ProductDto> findByVendorName(String vendor){
        List<Product> products = repo.findByAdminOrgName(vendor);
        return products.stream().map(this::dtoConverterHelper).toList();
    }

    public List<ProductDto> findByAvailable(Boolean available){
        List<Product> products = repo.findByAvailable(available);
        return products.stream().map(this::dtoConverterHelper).toList();
    }

    public List<ProductDto> findByDiscountGreaterThan(Integer discount){
        List<Product> products = repo.findByDiscountGreaterThan(discount);
        return products.stream().map(this::dtoConverterHelper).toList();
    }

    public List<ProductDto> findByPriceGreaterThan(Integer price){
        List<Product> products = repo.findByProductPriceGreaterThan(price);
        return products.stream().map(this::dtoConverterHelper).toList();
    }

    public List<ProductDto> findByPriceLessThan(Integer price){
        List<Product> products = repo.findByProductPriceLessThan(price);
        return products.stream().map(this::dtoConverterHelper).toList();
    }

    public List<ProductDto> findByNameAndCategory(String productName,String category){
        List<Product> products = repo.findByProductNameAndCategory(productName,category);
        return products.stream().map(this::dtoConverterHelper).toList();
    }

    public List<ProductDto> findByNameOrCategory(String productName,String category){
        List<Product> products = repo.findByProductNameOrCategory(productName,category);
        return products.stream().map(this::dtoConverterHelper).toList();
    }


    public ProductDto dtoConverterHelper(Product product){

        return ProductDto.builder()
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .category(product.getCategory().name())
                .discount(product.getDiscount())
                .id(product.getId())
                .vendor(product.getAdmin()!=null?product.getAdmin().getOrgName():null)
                .warranty(product.getWarranty())
                .productImageName(product.getProductImageName())
                .available(product.getAvailable())
                .build();

    }
}
