package com.sr.mobile_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegisterDto {
    public String adminName;
    public String password;
    public String orgName;
    public String email;
    public Integer phoneNumber;

}
