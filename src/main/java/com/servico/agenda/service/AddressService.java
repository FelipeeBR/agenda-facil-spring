package com.servico.agenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servico.agenda.dto.AddressDTO;
import com.servico.agenda.model.Address;
import com.servico.agenda.model.User;
import com.servico.agenda.repository.AddressRepository;
import com.servico.agenda.repository.UserRepository;



@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    public AddressDTO save(AddressDTO address) {
        User user = userRepository.findById(address.getUserId())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Address addressToSave = new Address(address);
        addressToSave.setUser(user);
        
        Address savedAddress = addressRepository.save(addressToSave);
        return new AddressDTO(savedAddress);
    }
}
