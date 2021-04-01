package com.farag.ultimate.repos;

import com.farag.ultimate.models.BorrowedBookID;
import com.farag.ultimate.models.BorrowedBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedBooksRepository extends JpaRepository<BorrowedBooks, BorrowedBookID> {
}