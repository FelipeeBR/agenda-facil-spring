package com.servico.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servico.agenda.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
