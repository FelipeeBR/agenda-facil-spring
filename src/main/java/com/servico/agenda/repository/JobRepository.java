package com.servico.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servico.agenda.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}
