package com.farag.ultimate.dtos.requests;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {
    private String userName;
    private String password;
}
