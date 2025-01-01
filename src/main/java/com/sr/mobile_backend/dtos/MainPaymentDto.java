package com.sr.mobile_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainPaymentDto {
    private String serverDate; //only for response
    private String serverTime; //only for response
    private String clientEmail;//extracted from jwt only for response
    private String paymentMethod;
    private String paymentStatus;
    /*private String date; not doing this now until theres a specific frontend
    private String time;*/
}
