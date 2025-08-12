package com.servico.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servico.agenda.dto.AddressDTO;
import com.servico.agenda.service.AddressService;

@RestController
@RequestMapping("/api/address/v1")
public class AddressControllerV1 {
    @Autowired
    private AddressService addressService;

    @PostMapping
    public AddressDTO newAddress(@RequestBody AddressDTO address) {
        return addressService.save(address);
    }
}
