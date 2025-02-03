package ru.itmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.UserDTO;
import ru.itmo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto) {
        UserDTO createdUser = userServiceImpl.createUser(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), userDto.getRoles().toString(), userDto.getOwnerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
