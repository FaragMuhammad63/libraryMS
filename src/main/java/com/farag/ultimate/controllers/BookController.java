package com.farag.ultimate.controllers;


import com.farag.ultimate.models.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id){
        return null;
    }
    @GetMapping({"/", ""})
    public ResponseEntity<Book> getAllBooks(){
        return null;
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book Book, @PathVariable Long id){
        return null;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        return null;
    }




}