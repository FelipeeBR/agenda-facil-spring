package com.servico.agenda.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servico.agenda.dto.AgendaDTO;
import com.servico.agenda.exceptions.UnsupportedValueException;
import com.servico.agenda.model.Agenda;
import com.servico.agenda.model.Job;
import com.servico.agenda.model.User;
import com.servico.agenda.repository.AgendaRepository;
import com.servico.agenda.repository.JobRepository;
import com.servico.agenda.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;

    public AgendaDTO save(AgendaDTO agenda) {
        User user = userRepository.findById(agenda.getUserId())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Job job = jobRepository.findById(agenda.getJobId())
        .orElseThrow(() -> new RuntimeException("Trabalho/Servico nao encontrado"));

        Agenda agendaToSave = new Agenda(agenda);
        agendaToSave.setUser(user);
        agendaToSave.setJob(job);
        Agenda savedAgenda = agendaRepository.save(agendaToSave);
        return new AgendaDTO(savedAgenda);
    }

    public List<AgendaDTO> getAgendasByJobId(Long jobId) {
        List<Agenda> agendas = agendaRepository.findByJobId(jobId);
        if(agendas.isEmpty()) {
            throw new UnsupportedValueException("Nenhuma agenda cadastrada.");
        }
        return agendas.stream().map(AgendaDTO::new).collect(Collectors.toList());
    }
}
