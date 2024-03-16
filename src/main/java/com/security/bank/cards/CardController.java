package com.security.bank.cards;

import com.security.bank.dto.CardDto;
import com.security.bank.entity.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("card")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/block")
    public ResponseEntity block(@RequestParam Long accountNumber,@RequestParam Long cardNumber){
        return cardService.block(accountNumber,cardNumber);
    }
    @PostMapping("/apply/new")
    public ResponseEntity applyNew(@RequestParam Long accNum, @RequestBody CardDto cardDto){
        return cardService.applyNew(accNum,cardDto);
    }
    @PutMapping("/setting")
    public ResponseEntity<Card> setting(@RequestBody Card card,@RequestParam Long cardNumber){
        return cardService.setting(card,cardNumber);
    }
}
