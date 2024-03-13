package com.example.diary.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.diary.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
 User findByUsernameAndPassword(String username, String password);
}

