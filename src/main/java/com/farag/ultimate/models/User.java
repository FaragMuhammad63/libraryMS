package com.farag.ultimate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.*;
import java.util.*;


@Entity
@Table(name = "user", schema = "public")
@NoArgsConstructor
@Setter
@Getter
@NamedEntityGraph(name = "user.roles", attributeNodes = @NamedAttributeNode("roles"))
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}