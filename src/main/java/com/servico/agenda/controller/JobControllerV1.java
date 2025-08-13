package com.servico.agenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servico.agenda.dto.JobDTO;
import com.servico.agenda.service.JobService;

@RestController
@RequestMapping("/api/jobs/v1")
public class JobControllerV1 {

    @Autowired
    private JobService jobService;

    @PostMapping
    public JobDTO newJob(@RequestBody JobDTO job) {
        return jobService.save(job);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JobDTO> getAll() {
        List<JobDTO> jobs = jobService.getAll();
        return jobs;
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JobDTO> findByUserId(@PathVariable("userId") Long userId) {
        List<JobDTO> jobs = jobService.findByUserId(userId);
        return jobs;
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public JobDTO update(@PathVariable("id") Long id, @RequestBody JobDTO job) {
        return jobService.update(id, job);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public JobDTO delete(@PathVariable("id") Long id) {
        return jobService.delete(id);
    }
}
