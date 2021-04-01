package com.farag.ultimate.services;

import com.farag.ultimate.dtos.requests.LoginRequest;
import com.farag.ultimate.dtos.requests.RegisterUserRequest;
import com.farag.ultimate.dtos.responses.JWTResponse;
import com.farag.ultimate.exceptions.AlreadyExistException;
import com.farag.ultimate.exceptions.BadRequestException;
import com.farag.ultimate.exceptions.NotAuthorizedUserException;
import com.farag.ultimate.exceptions.NotFoundException;

public interface AuthService {
    public JWTResponse login(LoginRequest request) throws BadRequestException, NotAuthorizedUserException;
    public JWTResponse registerUser(RegisterUserRequest request) throws AlreadyExistException, BadRequestException, NotAuthorizedUserException, NotFoundException;
}
