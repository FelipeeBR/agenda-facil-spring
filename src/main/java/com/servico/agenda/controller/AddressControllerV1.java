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

    // Lista todos endereços cadastrados
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AddressDTO> getAll() {
        List<AddressDTO> addresses = addressService.getAll();
        return addresses;
    }

    // Lista todos endereços cadastrados de um determinado usuário
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AddressDTO> findByUserId(@PathVariable("userId") Long userId) {
        List<AddressDTO> addresses = addressService.findByUserId(userId);
        return addresses;
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressDTO update(@PathVariable("id") Long id, @RequestBody AddressDTO address) {
        return addressService.update(id, address);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressDTO delete(@PathVariable("id") Long id) {
        return addressService.delete(id);
    }
}
