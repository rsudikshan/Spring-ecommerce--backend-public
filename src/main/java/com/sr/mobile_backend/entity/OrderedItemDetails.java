package com.sr.mobile_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderedItemDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id" , nullable = false)
    private Product productId;


    @ManyToOne
    @JoinColumn(name = "orgName", referencedColumnName = "orgName" , nullable = false)
    private Product orgName;

    private Long count;
    private Long totalPrice;
    private Long serverTotalPrice;
    private LocalDate localDate;//order date
    private LocalTime localTime;//

    @ManyToOne
    @JoinColumn(name = "clientEmail",referencedColumnName = "email")
    private Client clientEmail;//client email

}
