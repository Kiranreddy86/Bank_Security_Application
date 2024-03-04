package com.security.bank.accounts;

import com.security.bank.dto.AccountDto;
import com.security.bank.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<User> createAccount(@RequestBody AccountDto accountDto, @PathVariable Long userId){
        return accountService.createAccount(accountDto,userId);
    }
}
