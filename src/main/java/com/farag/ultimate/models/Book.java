package com.farag.ultimate.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "BOOK")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Book implements Serializable {
    @Column(name = "id", nullable = false)
    @Id
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "price")
    private Integer price;
    @Column(name = "availale")
    private Boolean available;
    @ManyToOne
    private Publisher publisher;

}