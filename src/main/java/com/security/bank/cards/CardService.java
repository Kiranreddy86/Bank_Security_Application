package com.security.bank.cards;

import com.security.bank.dto.CardDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.Card;
import com.security.bank.entity.CardType;
import com.security.bank.repository.AccountRepo;
import com.security.bank.repository.CardRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final CardRepo cardRepo;
    private final AccountRepo accountRepo;

    public CardService(CardRepo cardRepo, AccountRepo accountRepo) {
        this.cardRepo = cardRepo;
        this.accountRepo = accountRepo;
    }

    public ResponseEntity block(Long accountNumber, Long cardNumber) {
        Card card = cardRepo.block(accountNumber,cardNumber);
        card.setStatus("Block");
        return ResponseEntity.ok(cardRepo.save(card));
    }

    public ResponseEntity applyNew(Long accNum, CardDto cardDto) {
        Account account= accountRepo.findByAccountnumber(accNum).get();
        Card card=new Card();
        card.setCardHolderName(cardDto.getCardHolderName());
        card.setPin(cardDto.getPin());
        card.setCardType(CardType.valueOf(cardDto.getCardType()));
        card.setStatus("ACTIVE");
        card.setAccount(account);
        return ResponseEntity.ok(cardRepo.save(card));
    }
}
