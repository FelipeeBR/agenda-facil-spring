package com.servico.agenda.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import com.servico.agenda.controller.JobControllerV1;
import com.servico.agenda.dto.JobDTO;
import com.servico.agenda.exceptions.UnsupportedValueException;
import com.servico.agenda.model.Address;
import com.servico.agenda.model.Job;
import com.servico.agenda.model.User;
import com.servico.agenda.repository.AddressRepository;
import com.servico.agenda.repository.JobRepository;
import com.servico.agenda.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    
    public JobDTO save(JobDTO job) {
        User user = userRepository.findById(job.getUserId())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Address address = addressRepository.findById(job.getAddressId())
        .orElseThrow(() -> new RuntimeException("Endereço nao encontrado"));

        Job jobToSave = new Job(job);
        jobToSave.setUser(user);
        jobToSave.setAddress(address);
        Job savedJob = jobRepository.save(jobToSave);
        return new JobDTO(savedJob);
    }

    public List<JobDTO> getAll() {
        List<Job> jobs = jobRepository.findAll();
        if(jobs.isEmpty()) {
            throw new UnsupportedValueException("Nenhum trabalho/servico cadastrado.");
        }

        CollectionModel<JobDTO> collectionModel = CollectionModel.of(jobs.stream().map(JobDTO::new).collect(Collectors.toList()));

        collectionModel.add(WebMvcLinkBuilder.linkTo(JobControllerV1.class).withRel("jobs"));

        return jobs.stream().map(JobDTO::new).collect(Collectors.toList());
    }

    public List<JobDTO> findByUserId(Long userId) {
        List<Job> jobs = jobRepository.findByUserId(userId);
        if(jobs.isEmpty()) {
            throw new UnsupportedValueException("Nenhum trabalho/servico cadastrado.");
        }

        EntityModel<JobDTO> entityModel = EntityModel.of(new JobDTO(jobs.get(0)));

        entityModel.add(WebMvcLinkBuilder.linkTo(JobControllerV1.class).slash(userId).withSelfRel());
        return jobs.stream().map(JobDTO::new).collect(Collectors.toList());
    }

    public JobDTO update(Long id, JobDTO job) {
        Optional<Job> jobToUpdate = jobRepository.findById(id);
        if(jobToUpdate.isEmpty()) {
            throw new UnsupportedValueException("Trabalho/Servico nao encontrado.");
        }
        Job jobToSave = jobToUpdate.get();
        jobToSave.setTitle(job.getTitle());
        jobToSave.setDescription(job.getDescription());
        Job savedJob = jobRepository.save(jobToSave);
        return new JobDTO(savedJob);
    }

    public JobDTO delete(Long id) {
        Optional<Job> job = jobRepository.findById(id);
        if(job.isPresent()) {
            Job jobToDelete = job.get();
            jobRepository.delete(jobToDelete);
            return new JobDTO(jobToDelete);
        } else {
            throw new UnsupportedValueException("Trabalho/Servico nao encontrado.");
        }
    }
}
