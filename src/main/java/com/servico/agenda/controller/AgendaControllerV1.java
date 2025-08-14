package com.servico.agenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servico.agenda.dto.AgendaDTO;
import com.servico.agenda.service.AgendaService;

@RestController
@RequestMapping("/api/agenda/v1")
public class AgendaControllerV1 {
    @Autowired
    private AgendaService agendaService;

    @PostMapping
    public AgendaDTO newAgenda(@RequestBody AgendaDTO agenda) {
        return agendaService.save(agenda);
    }

    @GetMapping(value = "/job/{jobId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AgendaDTO> getAgendasByJobId(@PathVariable("jobId") Long jobId) {
        return agendaService.getAgendasByJobId(jobId);
    }
}
