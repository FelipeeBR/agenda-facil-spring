package com.servico.agenda.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servico.agenda.dto.UserDTO;
import com.servico.agenda.exceptions.UnsupportedValueException;
import com.servico.agenda.model.User;
import com.servico.agenda.repository.UserRepository;

@Service
@Transactional
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
        if(user.getUsername().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank()) {
            throw new UnsupportedValueException("Os campos username, email e password são obrigatórios.");
        }
        if(findByEmail(user.getEmail()) != null) {
            throw new UnsupportedValueException("Email já cadastrado.");
        }
        if(findByUsername(user.getUsername()) != null) {
            throw new UnsupportedValueException("Username já cadastrado.");
        }
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

    public UserDTO update(Long userId, UserDTO user) {
        Optional<User> userToUpdate = userRepository.findById(userId);
        if(userToUpdate.isPresent()) {
            User userToSave = userToUpdate.get();
            userToSave.setUsername(user.getUsername());
            userToSave.setEmail(user.getEmail());
            userToSave.setPassword(user.getPassword());
            User savedUser = userRepository.save(userToSave);
            return new UserDTO(savedUser);
        }
        return null;
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            return new UserDTO(user);
        }
        return null;
    }

    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            return new UserDTO(user);
        }
        return null;
    }
}
