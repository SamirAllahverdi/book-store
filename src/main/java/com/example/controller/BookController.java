package com.example.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    @GetMapping
    public String getAll(){
        return "";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable String id){
        return "";
    }



}
