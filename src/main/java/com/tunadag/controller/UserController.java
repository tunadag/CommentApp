package com.tunadag.controller;

import com.tunadag.dto.request.UserCreateRequestDto;
import com.tunadag.dto.response.UserCreateResponseDto;
import com.tunadag.repository.entity.User;
import com.tunadag.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/orderbyname")
    public ResponseEntity<List<User>> getUsersByName(){
        return ResponseEntity.ok(userService.findOrderByName());
    }

    @GetMapping("/containsvalue")
    public ResponseEntity<List<User>> findAllByNameContains(String value){
        return ResponseEntity.ok(userService.findAllByNameContains(value).get());
    }

    @GetMapping("/containsemail")
    public ResponseEntity<List<User>> findAllByEmailContains(String value){
        return ResponseEntity.ok(userService.findAllByEmailContains(value).get());
    }

    @GetMapping("/endingemail")
    public ResponseEntity<List<User>> findAllByEmailEnding(String value){
        return ResponseEntity.ok(userService.findAllByEmailEnding(value).get());
    }

    @GetMapping("/login")
    public ResponseEntity<Optional<User>> login(String email, String password){
        return ResponseEntity.ok(userService.login(email, password));
    }

    @GetMapping("/controlpassword")
    public ResponseEntity<List<User>> controlPassword(int length){
        return ResponseEntity.ok(userService.passwordControl(length));
    }

    @GetMapping("/controlpassword2")
    public ResponseEntity<List<User>> controlPassword2(int length){
        return ResponseEntity.ok(userService.passwordControl2(length));
    }

    @GetMapping("/save")
    public ResponseEntity<User> save(String name, String surName, String email, String password){
        return ResponseEntity.ok(userService.save(name, surName, email, password));
    }

    @PostMapping("/savewithdto")
    public ResponseEntity<UserCreateResponseDto> saveWithDto(@RequestBody UserCreateRequestDto dto){
        return ResponseEntity.ok(userService.saveWithDto(dto));
    }

    @PostMapping("/savewithdto2")
    public ResponseEntity<UserCreateResponseDto> saveWithDto2(@Valid @RequestBody UserCreateRequestDto dto){
        return ResponseEntity.ok(userService.saveWithDto2(dto));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id).get());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(Long id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/addfavproduct")
    public void addFavProduct(long userId, long productId){
        userService.addFavProduct(userId, productId);
    }

    @GetMapping("/getfavproducts")
    public ResponseEntity<List<Long>> getFavProduct(long userId){
        return ResponseEntity.ok(userService.getFavProducts(userId));
    }
}
