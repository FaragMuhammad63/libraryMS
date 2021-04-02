package com.farag.ultimate.controllers;

import com.farag.ultimate.dtos.requests.UpdateUserRequest;
import com.farag.ultimate.enums.RolesEnum;
import com.farag.ultimate.exceptions.BadRequestException;
import com.farag.ultimate.exceptions.NotFoundException;
import com.farag.ultimate.models.Role;
import com.farag.ultimate.models.User;
import com.farag.ultimate.repos.RoleRepository;
import com.farag.ultimate.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CrudService<User> service;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(@Qualifier("userService") CrudService<User> service, RoleRepository roleRepository) {
        this.service = service;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    @GetMapping({"/", ""})
    public ResponseEntity<User> getAllUsers() {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserRequest user, @PathVariable Long id) throws BadRequestException, NotFoundException {
        Set<Role> roles = new HashSet<>();
        if (user.getRoles() != null) {
            for (String role : user.getRoles()) {
                RolesEnum rolesEnum;
                try {
                    rolesEnum = RolesEnum.valueOf(role.toUpperCase());
                    roles.add(roleRepository.findByRole(rolesEnum)
                            .orElseThrow(() -> new BadRequestException("role: " + role + " is not existed")));
                } catch (IllegalArgumentException ex) {
                    throw new BadRequestException("role: " + role + " is not existed");
                }
            }
        }
        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setUserName(user.getUserName());
        updatedUser.setName(user.getName());
        updatedUser.setPhone(user.getPhone());
        updatedUser.setRoles(roles);

        return new ResponseEntity<>(service.update(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Boolean deleted = service.delete(id);
        if (deleted)
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
    }


}