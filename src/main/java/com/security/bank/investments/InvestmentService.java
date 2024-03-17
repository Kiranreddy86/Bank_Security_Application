package com.security.bank.investments;

import com.security.bank.dto.InvestmentDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.Investment;
import com.security.bank.entity.InvestmentType;
import com.security.bank.repository.AccountRepo;
import com.security.bank.repository.InvestmentRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InvestmentService {
    private final InvestmentRepo investmentRepo;
    private final AccountRepo accountRepo;
    public InvestmentService(InvestmentRepo investmentRepo, AccountRepo accountRepo) {
        this.investmentRepo = investmentRepo;
        this.accountRepo = accountRepo;
    }
    public ResponseEntity now(Long accountId, InvestmentDto investmentDto) {
        Account account=accountRepo.findById(accountId).get();
        Investment investment=new Investment();
        if(account.getBalance()>=investmentDto.getAmount()){
            investment.setAmount(investmentDto.getAmount());
            investment.setDuration(investmentDto.getDuration());
            investment.setInvestmentType(InvestmentType.valueOf(investmentDto.getInvestmentType()));
            accountRepo.save(account);
            investment.setAccount(account);
            investment.setUser(account.getUser());
            investmentRepo.save(investment);
        }else{
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok().build();
    }
}
