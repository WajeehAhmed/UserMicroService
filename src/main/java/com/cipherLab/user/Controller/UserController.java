package com.cipherLab.user.Controller;

import com.cipherLab.user.Entity.UserEntity;
import com.cipherLab.user.Exception.UserNotFoundException;
import com.cipherLab.user.Repository.UserRepository;
import com.cipherLab.user.constants.UserConstant;
import com.cipherLab.user.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAll() {
        var users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<UserEntity>> getUserById(@PathVariable Long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException(id);
        else return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users/byCellNumber/{cellNumber}")
    public ResponseEntity<List<UserEntity>> getBookByCellNumber(@PathVariable String cellNumber) {
        var user = userRepository.findByCellNumber(cellNumber);
        if (user.isEmpty()) throw new UserNotFoundException(cellNumber);
        else return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseDto> updateUserById(@RequestBody UserEntity newUserEntity, @PathVariable Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(id);
        } else {
            var oldUser = userOptional.get();
            oldUser.setName(newUserEntity.getName() != null ? newUserEntity.getName() : oldUser.getName());
            oldUser.setAge(newUserEntity.getAge() != null ? newUserEntity.getAge() : oldUser.getAge());
            newUserEntity.setLastChangeTs(LocalDateTime.now());
            userRepository.save(oldUser);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK, UserConstant.MESSAGE_200));
        }
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseDto> createUser(@RequestBody UserEntity newUserEntity) {
        newUserEntity.setLastChangeTs(LocalDateTime.now());
        userRepository.save(newUserEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(HttpStatus.CREATED, UserConstant.MESSAGE_201));
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<ResponseDto> deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException(id);
        else {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK, UserConstant.MESSAGE_200));
        }
    }
}
