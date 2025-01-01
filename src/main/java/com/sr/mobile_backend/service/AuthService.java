package com.sr.mobile_backend.service;

import com.sr.mobile_backend.dtos.AdminAuthDto;
import com.sr.mobile_backend.dtos.AdminRegisterDto;
import com.sr.mobile_backend.dtos.AuthDto;
import com.sr.mobile_backend.dtos.RegisterDto;
import com.sr.mobile_backend.entity.Admin;
import com.sr.mobile_backend.entity.Client;
import com.sr.mobile_backend.enums.Roles;
import com.sr.mobile_backend.repository.AdminRepo;
import com.sr.mobile_backend.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {


    AuthenticationManager manager;

    ClientRepo repo;

    AdminRepo adminRepo;

    JwtService service;

    AuthService(
            AuthenticationManager manager, ClientRepo repo, AdminRepo adminRepo, JwtService service
    ){
        this.manager = manager;
        this.repo = repo;
        this.adminRepo = adminRepo;
        this.service = service;
    }




    public Map<String,String> loginValidate(AuthDto dto){
        Map<String,String> response = new HashMap<>();
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getUsername(),dto.getPassword()
        ));
        if(authentication.isAuthenticated()){
            response.put("status","successful");
            response.put("BEARER ",service.generateToken(dto.getUsername()));
            return response;
        }
        response.put("status", "unsuccessful");
        return response;
    }

    public Map<String, String> register(RegisterDto dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        Map<String,String> response = new HashMap<>();

        if(repo.existsByEmail(dto.getEmail())){
            response.put("status","email already exists");
            return response;
        }

        Client client = Client.builder().username(dto.getUsername())
                .password(encoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .role(Roles.USER)
                .build();

        repo.save(client);
        response.put("status","register successful");
        response.put("email",dto.getEmail());
        response.put("username",dto.getUsername());

        return response;


    }

    public Map<String,String> adminRegister(AdminRegisterDto dto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        Map<String,String> response = new HashMap<>();
        if(dto!=null){
            if(!adminRepo.existsByEmailAndOrgName(dto.getEmail(),dto.getOrgName())){
                adminRepo.save(Admin.builder().
                        name(dto.getAdminName())
                        .password(encoder.encode(dto.getPassword()))
                        .email(dto.getEmail())
                        .phoneNumber(dto.getPhoneNumber())
                        .orgName(dto.getOrgName())
                        .roles(Roles.ADMIN)
                        .build()
                );
                response.put("status","successful");
                response.put("message", "register successful :"+dto.getOrgName());
                return response;
            }
            else {
                response.put("status","unsuccessful");
                response.put("message","email or organization name already registered");
            }
        }
        else {
            response.put("status","unsuccessful");
            response.put("message","invalid data");
        }
        return response;
    }

    public Map<String,String> adminLogin(AdminAuthDto dto){
        Map<String,String> response = new HashMap<>();
        Authentication authentication = manager.authenticate(new
                UsernamePasswordAuthenticationToken(
                        dto.getUsername(),dto.getPassword()
        ));
        if(authentication.isAuthenticated()){
            response.put("status","successful");
            response.put("username",dto.getUsername());
            response.put("BEARER ",service.generateToken(dto.getUsername()));
            return response;
        }
        response.put("status","unsuccessful");
        return response;
    }


}
