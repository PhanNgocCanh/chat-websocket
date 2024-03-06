package com.example.chatapp.repository;

import com.example.chatapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.id <> :userId")
    List<User> getAllUser(@Param("userId") String userId);
}
