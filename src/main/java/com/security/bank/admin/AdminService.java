package com.security.bank.admin;

import com.security.bank.dto.AdminDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.AccountType;
import com.security.bank.entity.Role;
import com.security.bank.entity.User;
import com.security.bank.repository.AccountRepo;
import com.security.bank.repository.NomineeRepo;
import com.security.bank.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final AccountRepo accountRepo;
    private final UserRepo userRepo;
    private final NomineeRepo nomineeRepo;
    public AdminService(AccountRepo accountRepo, UserRepo userRepo, NomineeRepo nomineeRepo) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
        this.nomineeRepo = nomineeRepo;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    public ResponseEntity register(AdminDto adminDto) {
        User user =new User();
        user.setName(adminDto.getName());
        user.setUsername(adminDto.getUsername());
        user.setAddress(adminDto.getAddress());
        String bCryptPasswordEncoder=passwordEncoder.encode(adminDto.getPassword());
        user.setPassword(bCryptPasswordEncoder);
        user.setIdentityProof(adminDto.getIdentityProof());
        Role role=new Role();
        role.setRoleName("ROLE_ADMIN");
        user.setRoles(role);
        userRepo.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    public ResponseEntity<User> getUserByName(String username) {
        return ResponseEntity.ok(userRepo.findByUsername(username).get());
    }
    @Transactional
    public ResponseEntity<User> deleteUser(String username) {
        User user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Account> accountList = accountRepo.findAllByUserId(user.getId());
        for (Account account : accountList) {
            nomineeRepo.deleteAllByAccountId(account.getId());
        }
        accountRepo.deleteAll(accountList);
        userRepo.delete(user);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<Account> deactivate(Long userId, Long accountId) {
        User user = userRepo.findById(userId).orElse(null);
        Account account = accountRepo.findById(accountId).orElse(null);
        if (user == null || account == null) {
            return ResponseEntity.notFound().build();
        }
        account.setStatus("INACTIVE");
        account.setUser(user);
        accountRepo.save(account);
        return ResponseEntity.ok(account);
    }
    public ResponseEntity<Account> activate(Long userId, Long accountId) {
        User user=userRepo.findById(userId).orElse(null);
        Account account=accountRepo.findById(accountId).orElse(null);
        if(user==null || account ==null){
            return ResponseEntity.notFound().build();
        }
        account.setStatus("ACTIVE");
        userRepo.save(user);
        account.setUser(user);
        return ResponseEntity.ok(accountRepo.save(account));
    }
    public ResponseEntity<List<Account>> getActiveAccountList() {
        List<Account> accountList=accountRepo.findAllActiveAccounts();
        if(accountList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accountList);
    }
    public ResponseEntity<List<Account>> getInActiveAccountList() {
        List<Account> accountList=accountRepo.findAllInActiveAccounts();
        if(accountList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accountList);
    }

    public ResponseEntity<List<Account>> getActiveAccountListByType(AccountType accountType) {
        List<Account> accountList=accountRepo.findAllActiveAccounts();
        if(accountList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<Account> filteredAccounts = accountList.stream()
                .filter(account -> account.getAccountType().equals(accountType))
                .toList();
        if(filteredAccounts.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(filteredAccounts);
    }

    public ResponseEntity<List<Account>> getInActiveAccountListByType(AccountType accountType) {
        List<Account> accountList=accountRepo.findAllActiveAccounts();
        if(accountList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<Account> filteredAccounts = accountList.stream()
                .filter(account -> account.getAccountType().equals(accountType))
                .toList();
        if(filteredAccounts.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(filteredAccounts);
    }
}
