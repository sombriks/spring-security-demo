package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MyLoginRepository extends JpaRepository<MyLogin, Long> {
    @Query("select l from MyLogin l where l.email = :email")
    Optional<MyLogin> getByLogin(String email);
}
