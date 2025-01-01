package com.sr.mobile_backend.repository;

import com.sr.mobile_backend.entity.Product;
import com.sr.mobile_backend.enums.Categories;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    Product findByProductName(String productName);
    Boolean existsByProductName(String productName);
    List<Product> findByProductNameContaining(String productName);
    List<Product> findByProductPriceLessThanEqual(Integer price);
    List<Product> findByProductPriceGreaterThanEqual(Integer price);
    List<Product> findByProductPriceLessThan(Integer price);
    List<Product> findByProductPriceGreaterThan(Integer price);
    List<Product> findByProductNameAndCategory(String productName,String category);
    List<Product> findByProductNameOrCategory(String productName,String category);
    List<Product> findByAdminOrgName(String vendorName);
    List<Product> findByAvailable(Boolean available);
    List<Product> findByDiscountGreaterThan(Integer discount);
    List<Product> findByCategory(String category);
    void deleteById(@NonNull Long id);


}
