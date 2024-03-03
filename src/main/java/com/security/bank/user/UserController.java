package com.security.bank.user;

import com.security.bank.dto.UserDto;
import com.security.bank.jwt.JwtRequest;
import com.security.bank.jwt.JwtResponse;
import com.security.bank.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody UserDto userDto){
        userService.createUser(userDto);
    }
}
