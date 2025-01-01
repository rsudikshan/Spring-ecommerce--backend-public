package com.sr.mobile_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;//this also used just for updating
    private String productName;
    private Integer productPrice;
    private String category;
    private String vendor;// this is used only for response the vendor when submitting is extracted from sec context
    private String warranty;
    private Integer discount;
    private String productImageName;//this is used only for response
    private Boolean available;
}
