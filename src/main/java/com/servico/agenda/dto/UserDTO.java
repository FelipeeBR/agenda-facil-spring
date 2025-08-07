package com.servico.agenda.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.servico.agenda.model.User;

public class UserDTO {
    private Long id;

    private String username;
    private String email;
    private String password;

    private Set<RoleDTO> roles;

    public UserDTO() {}

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        //this.password = user.getPassword();
        this.roles = user.getRoles().stream().map(role -> {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setRoleId(role.getRoleId());
            roleDTO.setName(role.getName());
            return roleDTO;
        }).collect(Collectors.toSet());;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }
}
