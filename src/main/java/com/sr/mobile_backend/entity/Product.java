package com.sr.mobile_backend.entity;

import com.sr.mobile_backend.enums.Categories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private Integer productPrice;

    @Enumerated(EnumType.STRING)
    private Categories category;
    private Boolean available;
    private String warranty;
    private Integer discount;
    private String productImageName;

    @ManyToOne
    @JoinColumn( name = "orgName", referencedColumnName = "orgName", nullable = false)
    private Admin admin;
}
