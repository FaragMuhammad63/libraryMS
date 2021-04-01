package com.farag.ultimate.dtos.requests;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {
    private String name;

    private String phone;

    private String userName;

    private String password;

    private Set<String> roles;
}
