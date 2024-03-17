package com.security.bank.admin;

import com.security.bank.dto.AdminDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.AccountType;
import com.security.bank.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @PostMapping("/add")
    public ResponseEntity register(@RequestBody AdminDto adminDto){
        return adminService.register(adminDto);
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUsers(){
        return adminService.getAllUsers();
    }
    @GetMapping("/getUserByName/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable String username){
        return adminService.getUserByName(username);
    }
    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username){
        return adminService.deleteUser(username);
    }
    @PutMapping("/account/deactivate")
    public ResponseEntity<Account> deactivate(@RequestParam Long userId,@RequestParam Long accountId){
        return adminService.deactivate(userId,accountId);
    }
    @PutMapping("/account/activate")
    public ResponseEntity<Account> activate(@RequestParam Long userId,@RequestParam Long accountId){
        return adminService.activate(userId,accountId);
    }
    @GetMapping("/account/getActiveAccountList")
    public ResponseEntity<List<Account>> getActiveAccountList(){
        return adminService.getActiveAccountList();
    }
    @GetMapping("/account/getInActiveAccountList")
    public ResponseEntity<List<Account>> getActiveInAccountList(){
        return adminService.getInActiveAccountList();
    }
    @GetMapping("/account/getActiveAccountList/{accountType}")
    public ResponseEntity<List<Account>> getActiveAccountListByType(@PathVariable AccountType accountType){
        return adminService.getActiveAccountListByType(accountType);
    }
    @GetMapping("/account/getInActiveAccountList/{accountType}")
    public ResponseEntity<List<Account>> getInActiveAccountListByType(@PathVariable AccountType accountType){
        return adminService.getInActiveAccountListByType(accountType);
    }

}
