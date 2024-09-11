package com.example.project.repository.blog;

import com.example.project.model.blog.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //@Query(value = "SELECT * FROM dev.users WHERE username = ?1 AND email = ?2", nativeQuery = true)
    Optional<User> findByUsernameAndEmail(String username, String email);
}
