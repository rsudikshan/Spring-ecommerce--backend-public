package com.sr.mobile_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsDto {

    private Long id;
    private String productName;//only for response extracted from id
    private Long count;
    private Long totalPrice;
    private String date; //only for response

}
