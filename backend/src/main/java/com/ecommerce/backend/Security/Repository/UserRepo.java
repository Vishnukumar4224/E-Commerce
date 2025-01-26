package com.ecommerce.backend.Security.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.backend.Security.Entity.Users;
import com.ecommerce.backend.Security.Enums.UserRole;


@Repository
public interface UserRepo extends JpaRepository<Users, Long>{

    Optional<Users> findFirstByEmail(String email);

    Users findByRole(UserRole userRole);

}
