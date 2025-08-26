package com.servico.agenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AgendaDTO> getAgendasByUserId(@PathVariable("userId") Long userId) {
        return agendaService.getAgendasByUserId(userId);
    }

    @GetMapping(value = "/client/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AgendaDTO> getAgendasByClientId(@PathVariable("clientId") Long clientId) {
        return agendaService.getAgendasByClientId(clientId);
    }

    @PatchMapping(value = "/{agendaId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AgendaDTO updateClient(@PathVariable Long agendaId, @RequestBody AgendaDTO agenda) {
        return agendaService.updateClient(agendaId, agenda.getClientId());
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AgendaDTO update(@PathVariable("id") Long id, @RequestBody AgendaDTO agenda) {
        return agendaService.update(id, agenda);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AgendaDTO delete(@PathVariable("id") Long id) {
        return agendaService.delete(id);
    }
}
