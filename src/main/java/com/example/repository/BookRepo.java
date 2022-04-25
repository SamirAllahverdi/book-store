package com.example.repository;

import com.example.domain.Book;
import com.example.filter.BookFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    @Query("select b from book b where b.id = :id and b.isDeleted=false")
    Optional<Book> findBookById(@Param("id") long id);

    @Query("select b from book b where b.isDeleted=false " +
            "and (:#{#filter.name} is null or b.name like %:#{#filter.name}% )" +
            "and (:#{#filter.title} is null or b.title like %:#{#filter.title}% )" +
            "and (:#{#filter.bookPage} is null or b.page = :#{#filter.bookPage} )" +
            "and (:#{#filter.authorId} is null or b.author.id = :#{#filter.authorId} )")
    List<Book> findAll(BookFilter filter, Pageable pageable);

    @Query("select b from book b where b.isDeleted=false and b.id <> :id and b.name = :name")
    Optional<Book> findByName(@Param("id") long id, @Param("name") String name);

}
