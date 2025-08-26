package com.servico.agenda.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private JavaMailSender javaMailSender;

    public AgendaDTO save(AgendaDTO agenda) {
        User user = userRepository.findById(agenda.getUserId())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Job job = jobRepository.findById(agenda.getJobId())
        .orElseThrow(() -> new RuntimeException("Trabalho/Servico nao encontrado"));


        enviarEmailAgenda(user.getEmail(), "Agendamento", "Agendamento para o trabalho/servico: " + job.getTitle());

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

    public List<AgendaDTO> getAgendasByClientId(Long clientId) {
        List<Agenda> agendas = agendaRepository.findByClientId(clientId);
        if(agendas.isEmpty()) {
            throw new UnsupportedValueException("Nenhuma agenda cadastrada.");
        }

        EntityModel<AgendaDTO> entityModel = EntityModel.of(new AgendaDTO(agendas.get(0)));
        entityModel.add(WebMvcLinkBuilder.linkTo(AgendaControllerV1.class).slash(clientId).withSelfRel());
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

    public AgendaDTO updateClient(Long id, Long clientId) {
        Optional<Agenda> agendaToUpdate = agendaRepository.findById(id);
        if(agendaToUpdate.isEmpty()) {
            throw new UnsupportedValueException("Agenda nao encontrada.");
        }

        User user = userRepository.findById(agendaToUpdate.get().getUser().getId())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        User client = userRepository.findById(clientId)
        .orElseThrow(() -> new RuntimeException("Usuário nao encontrado"));

        Job job = jobRepository.findById(agendaToUpdate.get().getJob().getId())
        .orElseThrow(() -> new RuntimeException("Trabalho/Servico nao encontrado"));

        // Profissional
        enviarEmailAgenda(user.getEmail(), "Agendamento", "O Cliente " + client.getUsername() + " agendou para o serviço: " + job.getTitle());
        
        // Cliente
        enviarEmailAgenda(client.getEmail(), "Agendamento", "Voce fez o Agendamento para o servico: " + job.getTitle());

        Agenda agendaToSave = agendaToUpdate.get();
        agendaToSave.setClientId(clientId);
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


    public String enviarEmailAgenda(String email, String assunto, String mensagem) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(assunto);
            message.setText(mensagem);
            javaMailSender.send(message);
            return "Email enviado com sucesso";
        } catch (Exception e) {
            return "Erro ao enviar email: " + e.getMessage();
        }
    }
}
