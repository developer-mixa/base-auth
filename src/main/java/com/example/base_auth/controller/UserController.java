package com.example.base_auth.controller;

import com.example.base_auth.models.*;
import com.example.base_auth.auth.JwtProvider;
import com.example.base_auth.model.UserDbEntity;
import com.example.base_auth.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        List<UserDTO> users = ((List<UserDbEntity>) userRepository.findAll())
                .stream()
                .map(userDbEntity -> userDbEntity.toUserDTO())
                .toList();
        log.info("get users");
        return users;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationRequest registrationRequest){
        log.info("register");
        UserDbEntity userDbEntity = new UserDbEntity();

        userDbEntity.setFirstName(registrationRequest.firstName);
        userDbEntity.setLastName(registrationRequest.lastName);
        userDbEntity.setUsername(registrationRequest.username);
        userDbEntity.setPassword(passwordEncoder.encode(registrationRequest.password));
        var savedUser = userRepository.save(userDbEntity);
        log.info("user is saved");
        return savedUser.getId().toString();
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody AuthRequest authRequest){
        UserDbEntity userDbEntity = userRepository.findByUsername(authRequest.username);

        if(authRequest.getPassword().length() < 8){
            return ResponseEntity.badRequest().body(new ErrorResponse("Password must be > 6"));
        }

        if(userDbEntity == null){
            return ResponseEntity.badRequest().body(new ErrorResponse("There's no user with this username!"));
        }

        if(!passwordEncoder.matches(authRequest.getPassword(), userDbEntity.getPassword())){
            return ResponseEntity.badRequest().body(new ErrorResponse("A wrong password!"));
        }

        String token = jwtProvider.generateToken(userDbEntity.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/user/get")
    public int getSpecialCode(){
        return 200;
    }

}
