package com.sr.mobile_backend.repository;

import com.sr.mobile_backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client,Long> {
    Optional<Client> findByUsername(String username);
    Boolean existsByEmail(String email);
    //could user Is in last of these prefix for exact matching
}
