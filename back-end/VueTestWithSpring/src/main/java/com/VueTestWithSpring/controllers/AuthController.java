package com.VueTestWithSpring.controllers;

import com.VueTestWithSpring.dto.LoginDto;
import com.VueTestWithSpring.dto.UserDto;
import com.VueTestWithSpring.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class AuthController {

    private final UserRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginHandler(@RequestBody LoginDto dto){
        return ResponseEntity.ok(userRepo.login(dto));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUpHandler(@RequestBody LoginDto dto){
        return ResponseEntity.ok(userRepo.signUp(dto));
    }
}
