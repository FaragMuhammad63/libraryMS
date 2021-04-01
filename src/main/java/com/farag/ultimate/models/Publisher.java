package com.farag.ultimate.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "PUBLISHER")
@Entity
@Getter
@Setter
public class Publisher implements Serializable {
    @Column(name = "id", nullable = false)
    @Id
    private Long id;
    @Column(name="name")
    private String name;


}