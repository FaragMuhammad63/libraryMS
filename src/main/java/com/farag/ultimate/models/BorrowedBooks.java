package com.farag.ultimate.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "BORROWED_BOOKS")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class BorrowedBooks implements Serializable {
    @EmbeddedId
    private BorrowedBookID borrowedBookID;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Column(name = "due_date", nullable = false)
    private Timestamp dueDate;
    @Column(name = "returned_date", nullable = false)
    private Timestamp returnedDate;

}