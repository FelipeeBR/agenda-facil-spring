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

import com.servico.agenda.dto.UserDTO;
import com.servico.agenda.service.UserService;

@RestController
@RequestMapping("/api/user/v1")
public class UserControllerV1 {
    @Autowired
    private UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getUsers() {
        List<UserDTO> usuarios = userService.getAll();
        return usuarios;
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    public UserDTO findByEmail(String email) {
        return userService.findByEmail(email);
    }

    public UserDTO findByUsername(String username) {
        return userService.findByUsername(username);
    }

    @PostMapping
    public UserDTO newUser(@RequestBody UserDTO user) {
        return userService.save(user);
    }

    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO delete(@PathVariable Long userId) {
        return userService.delete(userId);
    }

    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO update(@PathVariable Long userId, @RequestBody UserDTO user) {
        return userService.update(userId, user);
    }
}
