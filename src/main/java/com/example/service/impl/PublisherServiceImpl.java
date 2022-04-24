package com.example.service.impl;

import com.example.domain.Book;
import com.example.domain.User;
import com.example.dto.BookRequest;
import com.example.dto.BookResponse;
import com.example.exception.AlreadyExistException;
import com.example.exception.BookNotFoundException;
import com.example.exception.WrongPublisherException;
import com.example.mapper.BookMapper;
import com.example.repository.BookRepo;
import com.example.service.PublisherService;
import com.example.util.AuthenticationCreator;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final BookRepo bookRepo;
    private final AuthenticationCreator authenticationCreator;

    @Override
    public void get(String id) {

    }

    @Override
    public Long add(BookRequest request) {

        if (existByName(0, request.getName()))
            throw new AlreadyExistException(request.getName());

        User user = authenticationCreator.getCurrentUser();
        Book book = BookMapper.INSTANCE.toEntity(request);//TODO:
        book.setPublisher(user);

        System.out.println(book);

        return bookRepo.save(book).getId();
    }

    private boolean existByName(long id, String name) {
        return bookRepo.findByName(id, name).isPresent();
    }

    @Override
    public BookResponse edit(Long bookId, BookRequest request) {
        if (!Strings.isNullOrEmpty(request.getName()) && existByName(bookId, request.getName()))
            throw new AlreadyExistException(request.getName());
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        validateUser(book.getPublisher());
        changeIfPresent(request, book);
        return BookMapper.INSTANCE.toDto(bookRepo.save(book));
    }

    @Override
    public void delete(Long bookId) {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        validateUser(book.getPublisher());
        book.setDeleted(true);
        bookRepo.save(book);
    }

    private void validateUser(User publisher) {
        User loggedUser = authenticationCreator.getCurrentUser();
        if (!publisher.equals(loggedUser))
            throw new WrongPublisherException();
    }

    private void changeIfPresent(BookRequest request, Book book) {
        Optional.ofNullable(request.getName()).ifPresent(book::setName);
        Optional.ofNullable(request.getTitle()).ifPresent(book::setTitle);
        Optional.ofNullable(request.getPage()).ifPresent(book::setPage);
//        Optional.of(request.getAuthorId()).ifPresent(id -> book.setAuthor(new Author(id)));
        //TODO: author
    }
}
