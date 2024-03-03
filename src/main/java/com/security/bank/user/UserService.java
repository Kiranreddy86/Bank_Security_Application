package com.security.bank.user;

import com.security.bank.dto.UserDto;
import com.security.bank.entity.Role;
import com.security.bank.entity.User;
import com.security.bank.jwt.JwtRequest;
import com.security.bank.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }
    public void createUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encodedPassword);
        user.setAccountList(userDto.getAccountList());
        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        user.setIdentityProof(userDto.getIdentityProof());
        user.setInvestmentList(userDto.getInvestmentList());
        user.setNumber(userDto.getNumber());
        Role role = new Role();
        role.setRoleName("ROLE_CUSTOMER");
        user.setRoles(role);
        userRepository.save(user);
    }
}
