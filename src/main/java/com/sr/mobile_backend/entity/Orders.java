package com.sr.mobile_backend.entity;

import com.sr.mobile_backend.enums.PaymentMethod;
import com.sr.mobile_backend.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    private LocalTime time;
    /*private LocalDate clientSideDate; not doing this now until theres a specific frontend
    private LocalDate clientSideTime;*/


    @ManyToOne
    @JoinColumn(name = "clientEmail", referencedColumnName = "email")
    Client clientEmail;//client id

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;

}
//To do user registration date
//Order Date
//order tag