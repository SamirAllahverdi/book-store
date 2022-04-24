package com.example.repository;

import com.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select u from user u where u.email = :email and u.approveEmail = true")
    Optional<User> findByEmailAndApproveEmailTrue(@Param("email") String email);


    @Query("select u from user u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
