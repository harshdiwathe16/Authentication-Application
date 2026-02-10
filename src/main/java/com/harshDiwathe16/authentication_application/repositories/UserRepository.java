package com.harshDiwathe16.authentication_application.repositories;

import com.harshDiwathe16.authentication_application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>
{
    ///Custom Finder Methods
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
