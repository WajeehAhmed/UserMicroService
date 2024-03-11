package com.cipherLab.user.Controller;

import com.cipherLab.user.Entity.UserEntity;
import com.cipherLab.user.Exception.UserNotFoundException;
import com.cipherLab.user.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    public UserEntity updateUserById(@RequestBody UserEntity newUserEntity, @PathVariable Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(id);
        } else {
            var oldUser = userOptional.get();
            oldUser.setName(newUserEntity.getName() != null ? newUserEntity.getName() : oldUser.getName());
            oldUser.setAge(newUserEntity.getAge() != null ? newUserEntity.getAge() : oldUser.getAge());
            newUserEntity.setLastChangeTs(LocalDateTime.now());
            return userRepository.save(oldUser);
        }
    }

    @PostMapping("/users")
    public UserEntity createUser(@RequestBody UserEntity newUserEntity) {
        newUserEntity.setLastChangeTs(LocalDateTime.now());
        return userRepository.save(newUserEntity);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
