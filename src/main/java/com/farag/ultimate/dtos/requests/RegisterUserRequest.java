package com.farag.ultimate.dtos.requests;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest implements Serializable {
    private String name;

    private String phone;

    private String userName;

    private String password;

    private Set<String> roles;
}
