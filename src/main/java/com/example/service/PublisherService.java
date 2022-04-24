package com.example.service;

import com.example.dto.BookRequest;
import com.example.dto.BookResponse;

public interface PublisherService {

    void get(String id);
    Long add(BookRequest request);
    BookResponse edit(Long bookId, BookRequest request);
    void delete(Long bookId);
}
