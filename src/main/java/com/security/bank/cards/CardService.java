package com.security.bank.cards;

import com.security.bank.dto.CardDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.Card;
import com.security.bank.entity.CardType;
import com.security.bank.repository.AccountRepo;
import com.security.bank.repository.CardRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

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
        try {
            Account account = accountRepo.findByAccountnumber(accNum).orElse(null);
            if (account == null) {
                return ResponseEntity.notFound().build();
            }
            Card card = account.getCard();
            if (card == null) {
                card = new Card();
                long randomNumber = (long) (Math.random() * 8999999999999999L) + 1000000000000000L;
                card.setCardNumber(randomNumber);

            }
            Date allocationDate = new Date();
            card.setAllocationDate(allocationDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(allocationDate);
            calendar.add(Calendar.YEAR, 5);
            Date expiryDate = calendar.getTime();
            card.setExpiryDate(expiryDate);
            card.setCardHolderName(cardDto.getCardHolderName());
            card.setPin(cardDto.getPin());
            card.setCardType(CardType.valueOf(cardDto.getCardType()));
            if(CardType.valueOf(cardDto.getCardType()).equals(CardType.CREDIT_PREMIUM)){
                card.setDailyLimit(50000);
            }else if(CardType.valueOf(cardDto.getCardType()).equals(CardType.DEBIT_CLASSIC)){
                card.setDailyLimit(20000);
            }else if(CardType.valueOf(cardDto.getCardType()).equals(CardType.DEBIT_GLOBAL)){
                card.setDailyLimit(20000);
            }else{
                card.setDailyLimit(75000);
            }
            int randomCvv = (int) (Math.random() * 899 + 100);
            card.setCvv(randomCvv);
            card.setStatus("ACTIVE");
            accountRepo.save(account);
            card.setAccount(account);
            cardRepo.save(card);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
