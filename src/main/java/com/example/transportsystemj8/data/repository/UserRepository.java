package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.User;
import com.example.transportsystemj8.data.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndUserRole(String username, UserRole userRole);
    //User findByUsername(String username);
}
