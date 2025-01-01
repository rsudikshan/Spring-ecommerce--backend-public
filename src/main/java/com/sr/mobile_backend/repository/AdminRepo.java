package com.sr.mobile_backend.repository;

import com.sr.mobile_backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {
    Boolean existsByEmailAndOrgName(String email,String orgName);
    Optional<Admin> findByName(String name);
    Admin findByOrgName(String orgName);

}
