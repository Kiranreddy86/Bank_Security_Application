package com.security.bank.accounts;

import com.security.bank.dto.AccountDto;
import com.security.bank.dto.KycDto;
import com.security.bank.dto.NomineeDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.Nominee;
import com.security.bank.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<User> createAccount(@RequestBody AccountDto accountDto, @PathVariable Long userId){
        return accountService.createAccount(accountDto,userId);
    }
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Account>> getAllById(@PathVariable Long userId){
        return accountService.getAllById(userId);
    }
    @GetMapping("/balance")
    public ResponseEntity<Double> getByAccountNumber(@RequestParam Long accountNumber){
        return accountService.getByAccountNumber(accountNumber);
    }
    @GetMapping("/nominee")
    public ResponseEntity<Nominee> getBAccountNumber(@RequestParam Long accountNumber){
        return accountService.getNomineeByAccountNumber(accountNumber);
    }
    @PutMapping("/updateNominee/{accountId}")
    public ResponseEntity<Account> updateNomineeByAc(@RequestBody NomineeDto nomineeDto,@PathVariable Long accountId){
        return accountService.updateNomineeByAc(nomineeDto,accountId);
    }
    @GetMapping("/getKycDetails")
    public ResponseEntity<User> getKycDetails(@RequestParam Long accountNumber){
        return accountService.getUserByAn(accountNumber);
    }
    @PutMapping("/updateKyc/{accountId}")
    public ResponseEntity<User> updateKyc(@RequestBody KycDto kycDto,@PathVariable Long accountId){
        return accountService.updateKyc(kycDto,accountId);
    }
    @GetMapping("/getAccount/summary")
    public ResponseEntity<Account> getAccountSummary(@RequestParam Long accountNumber){
        return accountService.getAccountSummary(accountNumber);
    }
}
