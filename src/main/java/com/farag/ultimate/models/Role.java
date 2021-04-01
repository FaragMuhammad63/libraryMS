package com.farag.ultimate.models;


import com.farag.ultimate.enums.RolesEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "role", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RolesEnum role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;



}
