package com.example.controller;


import com.example.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

//    private final BookService bookService;

//    @GetMapping
//    public ResponseEntity<?> getAll(){
//        return "";
//    }

    @GetMapping("/{id}")
    public String get(@PathVariable String id){
        return "";
    }
}
