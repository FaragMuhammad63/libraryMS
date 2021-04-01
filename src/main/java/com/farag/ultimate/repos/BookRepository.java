package com.farag.ultimate.repos;

import com.farag.ultimate.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}