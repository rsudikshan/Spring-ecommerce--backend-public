package com.sr.mobile_backend.service;

import com.sr.mobile_backend.entity.Admin;
import com.sr.mobile_backend.entity.Client;
import com.sr.mobile_backend.repository.AdminRepo;
import com.sr.mobile_backend.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    ClientRepo clientRepo;

    @Autowired
    AdminRepo adminRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Admin> admin = adminRepo.findByName(username);
        Optional<Client> client = clientRepo.findByUsername(username);
        if(admin.isPresent()){

            return admin.get();
        }

        if(client.isPresent()){
            return client.get();
        }


        throw new UsernameNotFoundException("no such user");
    }
}
