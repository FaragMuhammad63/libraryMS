package com.farag.ultimate.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@Embeddable
public class BorrowedBookID implements Serializable {

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Book book;

}
