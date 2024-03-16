package com.security.bank.repository;

import com.security.bank.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepo extends JpaRepository<Card,Long> {
    Optional<Card> findByCardNumber(Long cardNumber);
    @Query(value = "SELECT * FROM card c LEFT JOIN account a on a.id=c.account_id where a.account_number=: an and c.card_number=: cn",nativeQuery = true)
    Card block(@Param("an") Long accountNumber,@Param("cn") Long cardNumber);
}
