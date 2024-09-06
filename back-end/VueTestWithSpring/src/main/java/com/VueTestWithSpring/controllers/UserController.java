package com.VueTestWithSpring.controllers;

import com.VueTestWithSpring.dto.UserDto;
import com.VueTestWithSpring.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserRepo userRepo;

    @PostMapping("/save")
    public ResponseEntity<UserDto> insertUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userRepo.save(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUsersById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userRepo.findUsersById(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<UserDto> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepo.findAllUsers());
    }

    @DeleteMapping("/{id}/{date}")
    public ResponseEntity<UserDto> deleteUserHandler(@PathVariable("id") String id, @PathVariable("date") String date) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepo.deleteUser(id, date));
    }

    @PutMapping("/{id}/{date}")
    public ResponseEntity<UserDto> updateUserHandler(@PathVariable("id") String id, @PathVariable("date") String date, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepo.updateUser(id, date, userDto));
    }

    @PutMapping("/change-profile")
    public ResponseEntity<UserDto> changeProfile(@RequestParam String id, @RequestParam String date, @RequestParam MultipartFile file){
        return  ResponseEntity.status(HttpStatus.OK).body(userRepo.uploadPhoto(id,date,file));
    }
}
