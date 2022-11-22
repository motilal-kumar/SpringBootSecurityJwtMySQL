package com.neosoft.springboot.security.jwt.mysql.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neosoft.springboot.security.jwt.mysql.model.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByUsername(String username);

}
