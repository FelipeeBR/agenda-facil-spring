package com.servico.agenda.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import com.servico.agenda.controller.AgendaControllerV1;
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

        EntityModel<AgendaDTO> entityModel = EntityModel.of(new AgendaDTO(agendas.get(0)));
        entityModel.add(WebMvcLinkBuilder.linkTo(AgendaControllerV1.class).slash(jobId).withSelfRel());
        return agendas.stream().map(AgendaDTO::new).collect(Collectors.toList());
    }

    public List<AgendaDTO> getAgendasByUserId(Long userId) {
        List<Agenda> agendas = agendaRepository.findByUserId(userId);
        if(agendas.isEmpty()) {
            throw new UnsupportedValueException("Nenhuma agenda cadastrada.");
        }

        EntityModel<AgendaDTO> entityModel = EntityModel.of(new AgendaDTO(agendas.get(0)));
        entityModel.add(WebMvcLinkBuilder.linkTo(AgendaControllerV1.class).slash(userId).withSelfRel());
        return agendas.stream().map(AgendaDTO::new).collect(Collectors.toList());
    }

    public AgendaDTO update(Long id, AgendaDTO agenda) {
        Optional<Agenda> agendaToUpdate = agendaRepository.findById(id);
        if(agendaToUpdate.isEmpty()) {
            throw new UnsupportedValueException("Agenda nao encontrada.");
        }
        Agenda agendaToSave = agendaToUpdate.get();
        agendaToSave.setDateTime(agenda.getDateTime());
        agendaToSave.setStatus(agenda.getStatus());
        Agenda savedAgenda = agendaRepository.save(agendaToSave);
        return new AgendaDTO(savedAgenda);
    }

    public AgendaDTO delete(Long id) {
        Optional<Agenda> agenda = agendaRepository.findById(id);
        if(agenda.isEmpty()) {
            throw new UnsupportedValueException("Agenda nao encontrada.");
        }
        Agenda agendaToDelete = agenda.get();
        agendaRepository.delete(agendaToDelete);
        return new AgendaDTO(agendaToDelete);
    }
}
