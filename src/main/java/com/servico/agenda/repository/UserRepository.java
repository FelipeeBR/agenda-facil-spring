package com.servico.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servico.agenda.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
