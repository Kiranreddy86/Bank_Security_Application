package com.security.bank.cards;

import com.security.bank.entity.Card;
import com.security.bank.repository.CardRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final CardRepo cardRepo;

    public CardService(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    public ResponseEntity block(Long accountNumber, Long cardNumber) {
        Card card = cardRepo.block(accountNumber,cardNumber);
    }
}
