package com.servico.agenda.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servico.agenda.dto.AddressDTO;
import com.servico.agenda.exceptions.UnsupportedValueException;
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

    public List<AddressDTO> getAll() {
        List<Address> addresses = addressRepository.findAll();
        if(addresses.isEmpty()) {
            throw new UnsupportedValueException("Nenhum endereço cadastrado.");
        }
        return addresses.stream().map(AddressDTO::new).collect(Collectors.toList());
    }

    public List<AddressDTO> findByUserId(Long userId) {
        List<Address> address = addressRepository.findByUserId(userId);
        if(address.isEmpty()) {
            throw new UnsupportedValueException("Nenhum endereço cadastrado.");
        }
        return address.stream().map(AddressDTO::new).collect(Collectors.toList());
    }

    public AddressDTO update(Long id, AddressDTO address) {
        Optional<Address> addressToUpdate = addressRepository.findById(id);
        if(addressToUpdate.isPresent()) {
            if(address.getStreet().isBlank() || address.getNumber().isBlank() || address.getCity().isBlank() || address.getState().isBlank()) {
                throw new UnsupportedValueException("Todos os campos devem ser preenchidos.");
            }
        }
        Address addressToSave = addressToUpdate.get();
        addressToSave.setStreet(address.getStreet());
        addressToSave.setNumber(address.getNumber());
        addressToSave.setCity(address.getCity());
        addressToSave.setState(address.getState());
        Address savedAddress = addressRepository.save(addressToSave);
        return new AddressDTO(savedAddress);
    }

    public AddressDTO delete(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if(address.isPresent()) {
            Address addressToDelete = address.get();
            addressRepository.delete(addressToDelete);
            return new AddressDTO(addressToDelete);
        }
        return null;
    }
}
