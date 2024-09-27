package com.example.project.repository.blog;

import com.example.project.model.blog.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsernameAndEmail(String username, String email);

    @Query(value = "SELECT email, password FROM dev.users WHERE email = ?1 AND password = ?2", nativeQuery = true)
    List<Object[]> findByEmailAndPassword(String email, String password);
}
