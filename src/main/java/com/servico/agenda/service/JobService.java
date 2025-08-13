package com.servico.agenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servico.agenda.dto.JobDTO;
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
}
