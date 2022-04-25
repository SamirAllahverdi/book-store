package com.example.service;

import com.example.domain.Author;
import com.example.domain.Book;
import com.example.domain.User;
import com.example.dto.*;
import com.example.exception.AlreadyExistException;
import com.example.exception.BookNotFoundException;
import com.example.repository.AuthorRepo;
import com.example.repository.BookRepo;
import com.example.service.impl.PublisherServiceImpl;
import com.example.util.AuthenticationCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    public User DUMMY_USER;
    @Mock
    private BookRepo bookRepo;
    @Mock
    private AuthenticationCreator authenticationCreator;
    @Mock
    private AuthorRepo authorRepo;
    private PublisherService publisherService;
    private Book DUMMY_BOOK;
    private BookRequest DUMMY_BOOK_REQUEST;
    private Author DUMMY_AUTHOR;

    @BeforeEach
    void init() {
        publisherService = new PublisherServiceImpl(bookRepo, authenticationCreator, authorRepo);

        LocalDate authorBirtDate = LocalDate.now();

        DUMMY_USER = User.builder()
                .firstName("Samir")
                .lastName("Allahverdiyev")
                .build();

        DUMMY_AUTHOR = Author.builder()
                .fullName("Fyodor Dostoyevsky")
                .birthDate(authorBirtDate)
                .build();

        DUMMY_BOOK = Book.builder()
//                .id(1L)
                .name("Crime and Punishment")
                .page(700)
                .title("Crime and Punishment is a novel by the Russian author Fyodor Dostoevsky")
                .author(DUMMY_AUTHOR)
                .publisher(DUMMY_USER)
                .build();

        DUMMY_BOOK_REQUEST = BookRequest.builder()
                .name("Crime and Punishment")
                .page(700)
                .title("Crime and Punishment is a novel by the Russian author Fyodor Dostoevsky")
                .author(
                        AuthorRequest.builder()
                                .fullName("Fyodor Dostoyevsky")
                                .birthDate(authorBirtDate)
                                .build()
                ).build();

    }

    @Test
    public void testAddSuccess() {
        when(bookRepo.save(DUMMY_BOOK)).thenReturn(DUMMY_BOOK);
        when(authorRepo.findByFullName(DUMMY_BOOK_REQUEST.getAuthor().getFullName())).thenReturn(Optional.ofNullable(DUMMY_AUTHOR));

        Long bookId = publisherService.add(DUMMY_BOOK_REQUEST);
        assertEquals(DUMMY_BOOK.getId(), bookId);
    }

    @Test
    public void testAddThrowsExceptionIfAlreadyExistsHappen() {
        long id = 0L;
        when(bookRepo.findByName(id, DUMMY_BOOK_REQUEST.getName())).thenReturn(Optional.of(DUMMY_BOOK));
        assertThatThrownBy(() -> publisherService.add(DUMMY_BOOK_REQUEST))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    public void testEditSuccess() {
        long bookId = 1L;

        when(bookRepo.findBookById(bookId)).thenReturn(Optional.ofNullable(DUMMY_BOOK));
        when(bookRepo.save(DUMMY_BOOK)).thenReturn(DUMMY_BOOK); //TODO:
        when(authenticationCreator.getCurrentUser()).thenReturn(DUMMY_USER); //TODO:

        BookResponse bookResponse = publisherService.edit(bookId, DUMMY_BOOK_REQUEST);

        assertTrue("Book Response is null", bookResponse != null);
        assertEquals(bookResponse.getName(), DUMMY_BOOK.getName());
        assertEquals(bookResponse.getTitle(), DUMMY_BOOK.getTitle());
        assertEquals(bookResponse.getPage(), DUMMY_BOOK.getPage());
        assertEquals(bookResponse.getCreatedAt(), DUMMY_BOOK.getCreatedAt());

        AuthorResponse authorResponse = bookResponse.getAuthor();
        Author author = DUMMY_BOOK.getAuthor();
        assertTrue("Author Response is null", authorResponse != null);
        assertEquals(authorResponse.getFullName(), author.getFullName());
        assertEquals(authorResponse.getBirthDate(), author.getBirthDate());

    }

    @Test
    public void testEditThrowsExceptionIfAlreadyExistsHappen() {
        long id = 1L;
        when(bookRepo.findByName(id, DUMMY_BOOK_REQUEST.getName())).thenReturn(Optional.of(DUMMY_BOOK));
        assertThatThrownBy(() -> publisherService.edit(id, DUMMY_BOOK_REQUEST))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    public void testDeleteThrowsExceptionIfCustomNotFoundExceptionHappen() {
        long bookId = 3L;
        when(bookRepo.findBookById(bookId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> publisherService.delete(bookId))
                .isInstanceOf(BookNotFoundException.class);
    }

}
