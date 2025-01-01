package com.sr.mobile_backend.controllers;

import com.sr.mobile_backend.dtos.ProductDto;
import com.sr.mobile_backend.entity.Product;
import com.sr.mobile_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    ProductService productService;

    ProductController(ProductService productService){
        this.productService =productService;
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("/searchByProductName")
    public ResponseEntity<ProductDto> searchProduct(@RequestBody ProductDto dto){
        return ResponseEntity.ok().body(productService.searchByProductName(dto.getProductName()));
    }

    @GetMapping("/getAllProductsByCategory")
    public ResponseEntity<List<ProductDto>> getCategory(@RequestBody ProductDto dto){
        return ResponseEntity.ok().body(productService.findByCategory(dto.getCategory()));
    }

    @GetMapping("/doesProductExist")
    public ResponseEntity<Boolean> doesProductExist(@RequestBody ProductDto dto){
        return ResponseEntity.ok().body(productService.existsByName(dto.getProductName()));
    }

    @GetMapping("/searchSimilarProducts")
    public ResponseEntity<List<ProductDto>> searchSimilarProducts(@RequestBody ProductDto dto){
        return ResponseEntity.ok().body(productService.getAllSimilarProducts(dto.getProductName()));
    }

    @GetMapping("/productsWithCostLessThan")
    public ResponseEntity<List<ProductDto>> findProductsWithPriceLessThan(@RequestBody ProductDto dto){
        return ResponseEntity.ok().body(productService.findByPriceLessThan(dto.getProductPrice()));
    }

    @GetMapping("/productsWithCostHigherThan")
    public ResponseEntity<List<ProductDto>> findProductsWithPriceGreaterThan(@RequestBody ProductDto dto){
        return ResponseEntity.ok().body(productService.findByPriceGreaterThan(dto.getProductPrice()));
    }

    @GetMapping("/productsWithCostHigherOrEqualTo")
    public ResponseEntity<List<ProductDto>> findProductsWithPriceGreaterOrEqual
            (@RequestBody ProductDto dto){
        return ResponseEntity.ok().body(productService.findInPriceRangeGreaterThanEqual(dto.getProductPrice()));
    }

    @GetMapping("/productsWithCostLessOrEqualTo")
    public ResponseEntity<List<ProductDto>> findProductsWithPriceSmallerOrEqual
            (@RequestBody ProductDto dto){
        return ResponseEntity.ok().body(productService.findInPriceRangeLessThanEqual(dto.getProductPrice()));
    }


    @GetMapping("/findByVendorName")
    public ResponseEntity<List<ProductDto>> findByVendorName(@RequestBody ProductDto dto){
        return ResponseEntity.ok().body(productService.findByVendorName(dto.getVendor()));
    }

    @GetMapping("/findByAvailable")
    public ResponseEntity<List<ProductDto>> findByAvailable(){
        return ResponseEntity.ok().body(productService.findByAvailable(true));
    }

    @GetMapping("/findByUnAvailable")
    public ResponseEntity<List<ProductDto>> findByUnAvailable(){
        return ResponseEntity.ok().body(productService.findByAvailable(false));
    }

    @GetMapping("/findByDiscountGreaterThan")
    public ResponseEntity<List<ProductDto>> findByDiscountGreaterThan(@RequestBody ProductDto dto){
        return ResponseEntity.ok().body(productService.findByDiscountGreaterThan(dto.getDiscount()));
    }

    @GetMapping("/findByNameAndCategory")
    public ResponseEntity<List<ProductDto>> findByNameAndCategory(@RequestBody ProductDto dto){

        return ResponseEntity.ok().body(productService.findByNameAndCategory(dto.getProductName(),dto.getCategory()));
    }

    @GetMapping("/findByNameOrCategory")
    public ResponseEntity<List<ProductDto>> findByNameOrCategory(@RequestBody ProductDto dto){

        return ResponseEntity.ok().body(productService.findByNameOrCategory(dto.getProductName(),dto.getCategory()));
    }



}
