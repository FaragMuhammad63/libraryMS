package com.farag.ultimate.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BorrowedBookID that = (BorrowedBookID) o;

        if (!Objects.equals(user, that.user))
            return false;
        return Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }
}
