package com.farag.ultimate.services;

import com.farag.ultimate.dtos.requests.LoginRequest;
import com.farag.ultimate.dtos.requests.RegisterUserRequest;
import com.farag.ultimate.dtos.responses.JWTResponse;
import com.farag.ultimate.enums.RolesEnum;
import com.farag.ultimate.exceptions.AlreadyExistException;
import com.farag.ultimate.exceptions.BadRequestException;
import com.farag.ultimate.exceptions.NotAuthorizedUserException;
import com.farag.ultimate.exceptions.NotFoundException;
import com.farag.ultimate.models.Role;
import com.farag.ultimate.models.User;
import com.farag.ultimate.repos.RoleRepository;
import com.farag.ultimate.repos.UserRepo;
import com.farag.ultimate.security.JWTUserDetails;
import com.farag.ultimate.security.JWTUserDetailsService;
import com.farag.ultimate.security.JWTUtils;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImp implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUserDetailsService jwtUserDetailsService;
    private final RoleRepository roleRepository;
    private final JWTUtils jwtUtils;

    @Autowired
    public AuthServiceImp(AuthenticationManager authenticationManager, UserRepo userRepo, BCryptPasswordEncoder passwordEncoder, JWTUserDetailsService jwtUserDetailsService, RoleRepository roleRepository, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public JWTResponse login(LoginRequest request) throws BadRequestException, NotAuthorizedUserException {
        Authentication auth;
        try {
            auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        } catch (DisabledException ex) {

            User user = userRepo.findFirstByUserName(request.getUserName())
                    .orElseThrow(() -> new UsernameNotFoundException("UserName Not Exist"));
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new BadRequestException("BadCredentialsException");
            }

            throw new NotAuthorizedUserException("User is disabled");
        } catch (BadCredentialsException ex) {

            throw new BadRequestException("BadCredentialsException");
        }
        catch (Exception e){
            System.out.println(e);
            throw e;
        }

        return getJwtResponse(request, auth);
    }

    @Override
    @Transient
    public JWTResponse registerUser(RegisterUserRequest request) throws AlreadyExistException, BadRequestException, NotAuthorizedUserException, NotFoundException {
        Optional<User> user = userRepo.findFirstByUserName(request.getUserName());
        if (user.isPresent()) throw new AlreadyExistException("User Already Exist");
        User userToRegister = new User();
        userToRegister.setName(request.getName());
        userToRegister.setUserName(request.getUserName());
        userToRegister.setPhone(request.getPhone());
        userToRegister.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (String s : request.getRoles()) {

            RolesEnum roleEnum;
            try {
                roleEnum = RolesEnum.valueOf(s.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new BadRequestException("the role " + s + " is not existed");
            }
            Role role = roleRepository.findByRole(roleEnum)
                    .orElseThrow(() -> new NotFoundException("Role " + s + " is not found"));

            roles.add(role);
        }
        userToRegister.setRoles(roles);
        try {
            userRepo.save(userToRegister);
        } catch (DataIntegrityViolationException ex) {

            throw new BadRequestException("these data violate the database");

        }

        return login(new LoginRequest(userToRegister.getUserName(), request.getPassword()));
    }

    private JWTResponse getJwtResponse(LoginRequest request, Authentication auth) {

        SecurityContextHolder.getContext().setAuthentication(auth);
        JWTUserDetails user = (JWTUserDetails) jwtUserDetailsService.loadUserByUsername(request.getUserName());
        if (!jwtUserDetailsService.isUserVerified(request.getUserName())) {
            return null;
        }
        String token = jwtUtils.addAuthentication(user);
        return new JWTResponse(token);
    }

}
