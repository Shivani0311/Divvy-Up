package com.example.expensesharing.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expensesharing.entity.User;
public interface UserRepository extends JpaRepository<User, Long> {

}

