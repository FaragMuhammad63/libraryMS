
package com.farag.ultimate.controllers;

import com.farag.ultimate.dtos.requests.LoginRequest;
import com.farag.ultimate.dtos.requests.RegisterUserRequest;
import com.farag.ultimate.dtos.responses.JWTResponse;
import com.farag.ultimate.exceptions.AlreadyExistException;
import com.farag.ultimate.exceptions.BadRequestException;
import com.farag.ultimate.exceptions.NotAuthorizedUserException;
import com.farag.ultimate.exceptions.NotFoundException;
import com.farag.ultimate.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginRequest request) throws BadRequestException, NotAuthorizedUserException {
        return new ResponseEntity<JWTResponse>(authService.login(request), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<JWTResponse> signup(@RequestBody RegisterUserRequest request)
            throws BadRequestException, AlreadyExistException, NotAuthorizedUserException, NotFoundException {
        return new ResponseEntity<>(authService.registerUser(request), HttpStatus.CREATED);
    }

}