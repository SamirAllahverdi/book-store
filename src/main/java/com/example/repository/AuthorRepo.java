package com.example.repository;

import com.example.domain.Author;
import com.example.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepo extends JpaRepository<Author,Long > {

    Optional<Author> findByFullName(String fullName);
}
