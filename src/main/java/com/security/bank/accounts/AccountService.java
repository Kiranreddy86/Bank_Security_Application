package com.security.bank.accounts;

import com.security.bank.dto.AccountDto;
import com.security.bank.entity.*;
import com.security.bank.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    UserRepo userRepo;
    public ResponseEntity<User> createAccount(AccountDto accountDto, Long userId) {
        User user=userRepo.findById(userId).get();
        Account account =new Account();
        account.setAccountType(AccountType.valueOf(accountDto.getAccountTtype()));
        account.setBalance(accountDto.getBalance());
        account.setNominee(accountDto.getNominee());
        account.setProof(account.getProof());
        account.setStatus("ACTIVE");
        Card card=new Card();
        if(AccountType.valueOf(accountDto.getAccountTtype()).equals("SAVINGS")){
            card.setCardType(CardType.DEBIT_GLOBAL);
            card.setDailyLimit(40000);
            account.setAccountType(AccountType.SAVINGS);
            account.setInterestRate(270);
            account.setBranch(BranchType.BOB);

        }else if(AccountType.valueOf(accountDto.getAccountTtype()).equals("CURRENT")){
            card.setCardType(CardType.CREDIT_PREMIUM);
            card.setDailyLimit(50000);
            account.setAccountType(AccountType.CURRENT);
            account.setInterestRate(52);
            account.setBranch(BranchType.ICIC);
        }else if(AccountType.valueOf(accountDto.getAccountTtype()).equals("SALARY")){
            card.setCardType(CardType.CREDIT_MASTER);
            card.setDailyLimit(75000);
            account.setAccountType(AccountType.SALARY);
            account.setInterestRate(41);
            account.setBranch(BranchType.HDFC);
        }else{
            account.setAccountType(AccountType.PPF);
            account.setInterestRate(74);
            account.setBranch(BranchType.SBI);
        }
        account.setCard(card);
        List<Account> accountList = user.getAccountList();
        accountList.add(account);
        user.setAccountList(accountList);
        return ResponseEntity.ok(userRepo.save(user));
    }
}
