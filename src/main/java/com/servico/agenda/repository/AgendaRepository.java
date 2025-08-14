package com.servico.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servico.agenda.model.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

}
