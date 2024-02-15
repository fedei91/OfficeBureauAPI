package com.example.officebureauapi.repositories;

import com.example.officebureauapi.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer>, JpaSpecificationExecutor<Token> {

    Optional<Token> findByToken(String token);
}
