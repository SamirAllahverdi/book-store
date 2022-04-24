package com.example.controller;


import com.example.dto.BookRequest;
import com.example.dto.BookResponse;
import com.example.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @PostMapping("/books")
    public ResponseEntity<Object> add(@RequestBody BookRequest request) {
        var bookId = publisherService.add(request);
        return ResponseEntity.created(getLocation(bookId)).build();
    }

    @PatchMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> edit(@PathVariable Long bookId, @RequestBody BookRequest request) {
        var response = publisherService.edit(bookId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> edit(@PathVariable Long bookId) {
        publisherService.delete(bookId);
        return ResponseEntity.noContent().build();
    }

    //TODO: logging
    public static <T> URI getLocation(T id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
