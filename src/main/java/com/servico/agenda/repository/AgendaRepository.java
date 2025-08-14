package com.servico.agenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servico.agenda.model.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByUserId(Long userId);

    List<Agenda> findByJobId(Long jobId);
}
