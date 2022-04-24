package com.example.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {



    @GetMapping("books")
    public String getAll() {
        return "";
    }
}
