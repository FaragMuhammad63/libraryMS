package com.farag.ultimate.controllers;


import com.farag.ultimate.models.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Publishers")
public class PublisherController {

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisher(@PathVariable Long id){
        return null;
    }
    @GetMapping({"/", ""})
    public ResponseEntity<Publisher> getAllPublishers(){
        return null;
    }
    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@RequestBody Publisher Publisher, @PathVariable Long id){
        return null;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable Long id){
        return null;
    }




}