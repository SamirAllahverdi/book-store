package com.example.service;

import com.example.dto.BookResponse;
import com.example.filter.BookFilter;

import java.util.List;

public interface BookService {
    BookResponse get(Long id);
    List<BookResponse> getAll(BookFilter filter);
}
