package com.example.service.impl;

import com.example.dto.BookResponse;
import com.example.exception.BookNotFoundException;
import com.example.filter.BookFilter;
import com.example.mapper.BookMapper;
import com.example.repository.BookRepo;
import com.example.service.BookService;
import com.example.util.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    public BookResponse get(Long id) {
        return bookRepo.findById(id)
                .map(BookMapper.INSTANCE::toDto)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public List<BookResponse> getAll(BookFilter filter) {
        return bookRepo.findAll(filter, PageableFactory.getPageable(filter)).stream()
                .map(BookMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }


}
