package com.example.project.service.blog;

import com.example.project.exception.UserNotFoundException;
import com.example.project.model.blog.User;
import com.example.project.dto.blog.UserDTO;
import com.example.project.repository.blog.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> saveUser(User user) {
        Optional<User> existingUser = userRepository.findByUsernameAndEmail(user.getUsername(), user.getEmail());

        if (existingUser.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(userRepository.save(user));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, UserDTO updateUser) {
        return userRepository.findById(id).map( existingUser -> {
            existingUser.setUsername(updateUser.getUsername());
            existingUser.setEmail(updateUser.getEmail());

            return userRepository.save(existingUser);
        }).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
