package com.example.project.service.blog;

import com.example.project.dto.blog.UserLoginDTO;
import com.example.project.model.blog.User;
import com.example.project.dto.blog.UpdateUserDTO;
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

    public Optional<UserLoginDTO> loginUser(UserLoginDTO userLogin) {
        List<Object[]> results = userRepository.findByEmailAndPassword(userLogin.getEmail(), userLogin.getPassword());

        if (results.isEmpty()) {
            return Optional.empty();
        }

        Object[] row = results.get(0);
        String resultEmail = row[0].toString();
        String resultPassword = row[1].toString();

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(resultEmail);
        userLoginDTO.setPassword(resultPassword);

        return Optional.of(userLoginDTO);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, UpdateUserDTO updateUser) {
        return userRepository.findById(id).map( existingUser -> {
            existingUser.setUsername(updateUser.getUsername());
            existingUser.setEmail(updateUser.getEmail());

            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
