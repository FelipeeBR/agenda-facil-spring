package com.servico.agenda.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servico.agenda.dto.UserDTO;
import com.servico.agenda.model.User;
import com.servico.agenda.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAll() {
        List<User> usuarios = userRepository.findAll();
        return usuarios.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public UserDTO findById(Long userId) {
        Optional<User> usuario = userRepository.findById(userId);
        if(usuario.isPresent()) {
            return new UserDTO(usuario.get());
        }
        return null;
    }

    public UserDTO save(UserDTO user) {
        User userToSave = new User(user);
        User savedUser = userRepository.save(userToSave);
        return new UserDTO(savedUser);
    }

    public UserDTO delete(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User userToDelete = user.get();
            userRepository.delete(userToDelete);
            return new UserDTO(userToDelete);
        }
        return null;
    }
}
