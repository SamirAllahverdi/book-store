package com.example.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    @GetMapping("/{id}/books")
    public String get(@PathVariable String id){
        return "";
    }

    @PostMapping("/books")
    public String add(@PathVariable String id){
        return "";
    }

    @PatchMapping("/{id}/books")
    public String edit(@PathVariable String id){
        return "";
    }
}
