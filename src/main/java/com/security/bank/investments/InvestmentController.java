package com.security.bank.investments;

import com.security.bank.dto.InvestmentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invest")
public class InvestmentController {
    private final InvestmentService investmentService;
    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }
//    @PostMapping("/now")
//    public ResponseEntity now(@RequestParam Long accountId, @RequestBody InvestmentDto investmentDto){
//        return investmentService.now(accountId,investmentDto);
//    }
}
