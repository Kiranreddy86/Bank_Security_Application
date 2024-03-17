package com.security.bank.accounts;

import com.security.bank.dto.AccountDto;
import com.security.bank.dto.KycDto;
import com.security.bank.dto.NomineeDto;
import com.security.bank.entity.*;
import com.security.bank.repository.AccountRepo;
import com.security.bank.repository.CardRepo;
import com.security.bank.repository.NomineeRepo;
import com.security.bank.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.security.bank.entity.AccountType.*;

@Service
public class AccountService {
    private final UserRepo userRepo;
    private final AccountRepo accountRepo;
    private final CardRepo cardRepo;
    private final NomineeRepo nomineeRepo;

    public AccountService(UserRepo userRepo, AccountRepo accountRepo, CardRepo cardRepo, NomineeRepo nomineeRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.cardRepo = cardRepo;
        this.nomineeRepo = nomineeRepo;
    }
    public ResponseEntity<Account> createAccount(AccountDto accountDto, Long userId) {
        try {
            User user = userRepo.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Account account = new Account();
            account.setAccountType(AccountType.valueOf(accountDto.getAccountType()));
            account.setBalance(accountDto.getBalance());
            account.setStatus("ACTIVE");
            account.setProof(accountDto.getProof());
            account.setUser(user);
            Account savedAccount = accountRepo.save(account);
            Nominee nominee = accountDto.getNominee();
            if (nominee != null) {
                nominee.setAccount(savedAccount);
                nomineeRepo.save(nominee);
            }
            List<Account> accountList = user.getAccountList();
            if (accountList == null) {
                accountList = new ArrayList<>();
            }
            accountList.add(savedAccount);
            user.setAccountList(accountList);
            userRepo.save(user);
            return ResponseEntity.ok(savedAccount);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<Account>> getAllById(Long userId) {
        List<Account> accountList=accountRepo.getAllById(userId);
        return ResponseEntity.ok(accountList);
    }

    public ResponseEntity<Double> getByAccountNumber(Long accountNumber) {
        Account acc=accountRepo.findByAccountnumber(accountNumber).get();
        return ResponseEntity.ok(acc.getBalance());
    }
    public ResponseEntity<Nominee> getNomineeByAccountNumber(Long accountNumber) {
        Account acc=accountRepo.findByAccountnumber(accountNumber).get();
        return ResponseEntity.ok(acc.getNominee());
    }

    public ResponseEntity<Account> updateNomineeByAc(NomineeDto nomineeDto, Long accountId) {
        Account acc=accountRepo.findById(accountId).get();
        Nominee nominee=new Nominee();
        nominee.setName(nomineeDto.getName());
        nominee.setAccountNumber((nomineeDto.getAccountNumber()));
        nominee.setAge(nomineeDto.getAge());
        nominee.setGender(nomineeDto.getGender());
        nominee.setRelation(nomineeDto.getRelation());
        acc.setNominee(nominee);
        return ResponseEntity.ok(accountRepo.save(acc));
    }

    public ResponseEntity<User> getUserByAn(Long accountNumber) {
        Account acc=accountRepo.findByAccountnumber(accountNumber).get();
        User user=acc.getUser();
        user.setAccountList(null);
        user.setInvestmentList(null);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> updateKyc(KycDto kycDto, Long accountId) {
        Account acc=accountRepo.findById(accountId).get();
        User user=acc.getUser();
        user.setName(kycDto.getName());
        user.setAddress(kycDto.getAddress());
        user.setIdentityProof(kycDto.getIdentityProof());
        user.setAccountList(null);
        user.setInvestmentList(null);
        acc.setUser(user);
        userRepo.save(user);
        return ResponseEntity.ok(accountRepo.save(acc).getUser());
    }

    public ResponseEntity<Account> getAccountSummary(Long accountNumber) {
        Account acc=accountRepo.findByAccountnumber(accountNumber).get();
        acc.setUser(null);
        return ResponseEntity.ok(acc);
    }
}
