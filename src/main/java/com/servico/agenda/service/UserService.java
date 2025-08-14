package com.servico.agenda.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servico.agenda.controller.UserControllerV1;
import com.servico.agenda.dto.UserDTO;
import com.servico.agenda.exceptions.UnsupportedValueException;
import com.servico.agenda.model.User;
import com.servico.agenda.repository.UserRepository;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAll() {
        List<User> usuarios = userRepository.findAll();
        if(usuarios.isEmpty()) {
            throw new UnsupportedValueException("Nenhum usuário cadastrado.");
        }

        CollectionModel<UserDTO> collectionModel = CollectionModel.of(usuarios.stream().map(UserDTO::new).collect(Collectors.toList()));

        collectionModel.add(WebMvcLinkBuilder.linkTo(UserControllerV1.class).withRel("users"));
        return usuarios.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public UserDTO findById(Long userId) {
        Optional<User> usuario = userRepository.findById(userId);
        if(!usuario.isPresent()) {
            throw new UnsupportedValueException("Usuário nao encontrado.");
        }


        EntityModel<UserDTO> entityModel = EntityModel.of(new UserDTO(usuario.get()));

        entityModel.add(WebMvcLinkBuilder.linkTo(UserControllerV1.class).slash(userId).withSelfRel());

        return new UserDTO(usuario.get());
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

            UserDTO deletedUserDTO = new UserDTO(userToDelete);
            deletedUserDTO.add(WebMvcLinkBuilder.linkTo(UserControllerV1.class).slash(userId).withSelfRel());

            return deletedUserDTO;
        }
        return null;
    }

    public UserDTO update(Long userId, UserDTO user) {
        Optional<User> userToUpdate = userRepository.findById(userId);
        if(userToUpdate.isPresent()) {
            if(user.getUsername().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank()) {
                throw new UnsupportedValueException("Os campos username, email e password são obrigatórios.");
            }
            if(userRepository.existsByUsernameAndIdNot(user.getUsername(), userId)) {
                throw new UnsupportedValueException("Username já cadastrado.");
            }
            if(userRepository.existsByEmailAndIdNot(user.getEmail(), userId)) {
                throw new UnsupportedValueException("Email já cadastrado.");
            }

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
