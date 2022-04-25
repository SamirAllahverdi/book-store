package com.example.service;

import com.example.domain.Author;
import com.example.domain.Book;
import com.example.dto.AuthorResponse;
import com.example.dto.BookResponse;
import com.example.exception.BookNotFoundException;
import com.example.filter.BookFilter;
import com.example.repository.BookRepo;
import com.example.service.impl.BookServiceImpl;
import com.example.util.PageableFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepo bookRepo;
    private BookService bookService;

    private Book DUMMY_BOOK;

    @BeforeEach
    void init() {
        bookService = new BookServiceImpl(bookRepo);

        DUMMY_BOOK = Book.builder()
                .id(1L)
                .name("Crime and Punishment")
                .page(700)
                .title("Crime and Punishment is a novel by the Russian author Fyodor Dostoevsky")
                .createdAt(LocalDateTime.now())
                .author(
                        Author.builder()
                                .fullName("Fyodor Dostoyevsky")
                                .birthDate(LocalDate.now())
                                .build()
                ).build();
    }

    @Test
    public void testGetByIdSuccess() {
        long bookId = 1;

        when(bookRepo.findBookById(bookId)).thenReturn(Optional.of(DUMMY_BOOK));
        BookResponse bookResponse = bookService.get(bookId);

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
    public void testThrowsExceptionIfCustomNotFoundHappen() {
        long bookId = 1;
        when(bookRepo.findBookById(bookId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.get(bookId))
                .isInstanceOf(BookNotFoundException.class);
    }


    @Test
    public void testGetAllSuccess() {
        BookFilter filter = BookFilter.builder().build();
        when(bookRepo.findAll(filter, PageableFactory.getPageable(filter)))
                .thenReturn(Arrays.asList(DUMMY_BOOK, DUMMY_BOOK, DUMMY_BOOK));
        List<BookResponse> bookResponses = bookService.getAll(filter);
        assertEquals(bookResponses.size(), bookResponses.size());
    }

}
