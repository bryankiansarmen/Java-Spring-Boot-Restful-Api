package com.example.project.controller.blog;

import com.example.project.dto.blog.UpdateUserDTO;
import com.example.project.dto.blog.UserLoginDTO;
import com.example.project.model.blog.User;
import com.example.project.service.blog.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Optional<User> newUser =  userService.saveUser(user);

        if (newUser.isPresent()) {
            return ResponseEntity.ok(newUser.get());
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> userCredential(@RequestBody UserLoginDTO userLogin) {
        Optional<UserLoginDTO> userCredential = userService.loginUser(userLogin);

        if (userCredential.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("User login successfully!");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User invalid credential!");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();

        return ResponseEntity.ok(userList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUser) {
        User user = userService.updateUser(id, updateUser);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully.");
    }
}
