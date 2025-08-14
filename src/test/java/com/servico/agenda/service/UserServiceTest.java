package com.servico.agenda.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.servico.agenda.dto.RoleDTO;
import com.servico.agenda.dto.UserDTO;
import com.servico.agenda.exceptions.UnsupportedValueException;
import com.servico.agenda.model.Role;
import com.servico.agenda.model.User;
import com.servico.agenda.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;  

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private User savedUser;

    @BeforeEach
    public void setup(){
        Set<RoleDTO> roles = new HashSet<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("USER");
        roles.add(roleDTO);

        userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");
        userDTO.setRoles(roles);

        savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername(userDTO.getUsername());
        savedUser.setEmail(userDTO.getEmail());
        savedUser.setPassword(userDTO.getPassword());

        Set<Role> roleEntities = new HashSet<>();
        Role role = new Role();
        role.setName("USER");
        roleEntities.add(role);
        savedUser.setRoles(roleEntities);
    }

    @Test
    public void whenThereIsNoUsers() {
        assertThrows(UnsupportedValueException.class, () -> {
            userService.getAll();
        }); 
    }

    @Test
    public void findById() {
        
    }

    @Test
    public void saveAndReturnLinks() {
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO result = userService.save(userDTO);

        assertNotNull(result, "O retorno não deveria ser nulo");
        assertEquals(savedUser.getId(), result.getId(), "O Id retornado está incorreto");
        assertEquals(savedUser.getUsername(), result.getUsername(), "O username retornado está incorreto");
        assertTrue(result.getLinks().hasLink("self"), "O link self não existe");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void whenSaveUserFieldsAreEmpty() {
        UserDTO invalidUser = new UserDTO();
        invalidUser.setUsername("");
        invalidUser.setEmail("");
        invalidUser.setPassword("");

        assertThrows(UnsupportedValueException.class, () -> {
            userService.save(invalidUser);
        });
    }
        
}
    