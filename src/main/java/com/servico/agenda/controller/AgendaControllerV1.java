package com.servico.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
}
