package com.servico.agenda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servico.agenda.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
}
