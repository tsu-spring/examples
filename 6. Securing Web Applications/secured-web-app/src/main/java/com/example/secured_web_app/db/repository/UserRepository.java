package com.example.secured_web_app.db.repository;

import com.example.secured_web_app.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}