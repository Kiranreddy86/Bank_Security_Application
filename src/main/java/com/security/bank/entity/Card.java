package com.security.bank.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long cardNumber;
	String cardHolderName;
	@Enumerated(EnumType.STRING)
	private CardType cardType;
	private double dailyLimit;
	private int cvv;
	private Date allocationDate;
	private Date expiryDate;
	private Long pin;
	private String status;
	@OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
