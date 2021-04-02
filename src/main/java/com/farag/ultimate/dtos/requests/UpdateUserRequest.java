package com.farag.ultimate.dtos.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class UpdateUserRequest implements Serializable {
    private String name;
    private String userName;
    private String phone;
    private Set<String> roles;

}
