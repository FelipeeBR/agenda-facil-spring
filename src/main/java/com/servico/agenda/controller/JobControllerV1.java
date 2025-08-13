package com.servico.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
}
