package com.farag.ultimate.services;

import com.farag.ultimate.exceptions.AlreadyExistException;
import com.farag.ultimate.exceptions.BadRequestException;
import com.farag.ultimate.exceptions.NotFoundException;
import com.farag.ultimate.models.User;
import com.farag.ultimate.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("userService")
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User insert(User user) throws AlreadyExistException, BadRequestException {
        Optional<User> userOptional = userRepository.findByUserName(user.getUserName());
        if (userOptional.isPresent()) {
            throw new AlreadyExistException("user with username: " + user.getUserName() + " is already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("There's something wrong in your data");
        }
        return user;
    }

    @Override
    public User read(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User with id: " + id + " not found"));
    }

    @Transactional
    @Override
    public Boolean delete(Long id) {
        return userRepository.removeById(id) == 1;
    }

    @Override
    public User update(User updatedUser) throws NotFoundException {
        User finalUpdatedUser = updatedUser;
        User user = userRepository.findById(updatedUser.getId()).orElseThrow(
                () -> new NotFoundException("User with id: " + finalUpdatedUser.getId() + " not found"));
        updatedUser.setUserName(updatedUser.getUserName() == null ? user.getUserName() : updatedUser.getUserName());
        updatedUser.setName(updatedUser.getName() == null ? user.getName() : updatedUser.getName());
        updatedUser.setPhone(updatedUser.getPhone() == null ? user.getPhone() : updatedUser.getPhone());
        updatedUser.setRoles(updatedUser.getRoles() == null ? user.getRoles() : updatedUser.getRoles());
        updatedUser.setPassword(user.getPassword());
        updatedUser = userRepository.save(updatedUser);
        return updatedUser;
    }
}
