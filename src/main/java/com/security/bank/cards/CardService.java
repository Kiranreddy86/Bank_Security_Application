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
        Card card=account.getCard();
        if(card==null){
            card.setCardHolderName(cardDto.getCardHolderName());
            card.setPin(cardDto.getPin());
            card.setCardType(CardType.valueOf(cardDto.getCardType()));
            card.setStatus("ACTIVE");
            account.setCard(card);
            accountRepo.save(account);
            return ResponseEntity.ok(cardRepo.save(card));
        }
        return ResponseEntity.badRequest().build();

    }

    public ResponseEntity<Card> setting(Card card, Long cardNumber) {
        Card c=cardRepo.findByCardNumber(cardNumber).get();
        if(c!=null){
            CardType cardType=card.getCardType();
            c.setCardType(cardType);
            if(cardType==CardType.DEBIT_CLASSIC){
                c.setDailyLimit(40000);
            } else if (cardType==CardType.DEBIT_GLOBAL) {
                c.setDailyLimit(50000);
            } else if (cardType==CardType.CREDIT_PREMIUM) {
                c.setDailyLimit(75000);
            }else{
                c.setDailyLimit(100000);
            }
            c.setPin(card.getPin());
            return ResponseEntity.ok(cardRepo.save(c));
        }
        return ResponseEntity.notFound().build();
    }
}
