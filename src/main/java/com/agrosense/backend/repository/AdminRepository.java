package com.agrosense.backend.repository;

import com.agrosense.backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    Optional<Admin> findByEmail(String email);
    
    Optional<Admin> findByPhone(String phone);
    
    Optional<Admin> findByEmailOrPhone(String email, String phone);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
}
